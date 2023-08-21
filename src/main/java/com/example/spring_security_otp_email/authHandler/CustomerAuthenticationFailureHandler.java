package com.example.spring_security_otp_email.authHandler;

import com.example.spring_security_otp_email.entities.Customer;
import com.example.spring_security_otp_email.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomerAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private CustomerServices customerServices;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("email");
        System.out.println("onAuthenticationFailure email: " + email);
        request.setAttribute("email", email);

        String redirectURl = "/login?error&email=" + email;

        if (exception.getMessage().contains("OTP")) {
            redirectURl = "/login?otp=true&email= " + email;
        } else {
            Customer customer = customerServices.getCustomerByEmail(email);
            if (customer.isOTPRequired()) {
                redirectURl = "/login?otp=true&email=" + email;
            }
        }

        super.setDefaultFailureUrl(redirectURl);
        super.onAuthenticationFailure(request, response, exception);

    }
}
