package com.example.spring_security_otp_email.services.impl;

import com.example.spring_security_otp_email.Repo.CustomerRepo;
import com.example.spring_security_otp_email.entities.Customer;
import com.example.spring_security_otp_email.services.CustomerServices;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class CustomerServicesimpl implements CustomerServices {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void generateOneTimePassword(Customer customer) {
        String OTP  = RandomString.make(8);
        if (OTP != null) {
            String encodedOTP  = passwordEncoder.encode(OTP);
            customer.setOneTimePassword(encodedOTP);
            customer.setOtpRequestedTime(new Date());
            customerRepo.save(customer);

        }
    }

    @Override
    public void sendOTPEmail(Customer customer, String OTP) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("timsinarewon@gmail.com", "Mandip Timsina");
        helper.setTo(customer.getEmail());

        String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";

        String content = "<p>Hello " + customer.getEmail() + "</p>"
                + "<p>For security reason, you're required to use the following "
                + "One Time Password to login:</p>"
                + "<p><b>" + OTP + "</b></p>"
                + "<br>"
                + "<p>Note: this OTP is set to expire in 5 minutes.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);

    }

    @Override
    public void clearOTP(Customer customer) {
        customer.setOneTimePassword(null);
        customer.setOtpRequestedTime(null);
        customerRepo.save(customer);
    }
}
