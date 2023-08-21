package com.example.spring_security_otp_email.config;

import com.example.spring_security_otp_email.entities.Customer;
import com.example.spring_security_otp_email.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.naming.InsufficientResourcesException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BeforeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private CustomerServices customerServices;

    private final AuthenticationManager authenticationManager;



//    @Autowired
//    @Bean
//    @Primary
//    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
//    }

    @Autowired
    @Override
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(failureHandler);
    }

//    @Autowired
//    @Override
//    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
//        super.setAuthenticationSuccessHandler(successHandler);
//    }

    @Autowired
    public BeforeAuthenticationFilter(AuthenticationManager manager) {
        this.authenticationManager = manager;
        super.setAuthenticationManager(manager);
        setUsernameParameter("email");
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        Customer customer = customerServices.getCustomerByEmail(email);

        if (customer != null ) {
            if (customer.isOTPRequired()) {
                return super.attemptAuthentication(request, response);
            }
            System.out.println("attemptAuthentication - email: " + email);
            float spamScore =  getGoogleRecaptchaScore();
            if (spamScore < 0.5)
            try {
                customerServices.generateOneTimePassword(customer);
                throw new InsufficientResourcesException("OTP");
            }catch (InsufficientResourcesException e) {
                    throw new RuntimeException(e);

            }
        }
        return super.attemptAuthentication(request, response);
    }

    private float getGoogleRecaptchaScore() {
        return 0.3f;
    }


}
