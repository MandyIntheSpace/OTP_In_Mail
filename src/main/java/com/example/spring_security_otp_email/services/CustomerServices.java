package com.example.spring_security_otp_email.services;

import com.example.spring_security_otp_email.entities.Customer;
//import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;


@Transactional
public interface CustomerServices {
    void generateOneTimePassword(Customer customer);
    void sendOTPEmail(Customer customer, String OTP) throws MessagingException, UnsupportedEncodingException;
    void clearOTP(Customer customer);
}
