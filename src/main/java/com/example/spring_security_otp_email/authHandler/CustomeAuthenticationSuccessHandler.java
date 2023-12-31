//package com.example.spring_security_otp_email.authHandler;
//
//import com.example.spring_security_otp_email.details.CustomerDetails;
//import com.example.spring_security_otp_email.entities.Customer;
//import com.example.spring_security_otp_email.services.CustomerServices;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class CustomeAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    @Autowired
//    private CustomerServices customerServices;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//
//        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
//
//        Customer customer = customerDetails.getUsername();
//
//    }
//}
