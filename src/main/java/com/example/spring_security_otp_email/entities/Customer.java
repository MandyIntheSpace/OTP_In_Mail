package com.example.spring_security_otp_email.entities;

//import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String email;
    private String password;
    @Column(name = "one_time_password")
    private String oneTimePassword;

    @Column(name = "one_request_time")
    private Date otpRequestedTime;

    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;

    public boolean isOTPRequired() {
        if (this.getOneTimePassword() == null) {
            return false;
        }
        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = this.getOtpRequestedTime().getTime();

        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            return false;
        }
        return true;
    }

}
