package com.example.spring_security_otp_email.config;

import com.example.spring_security_otp_email.authHandler.CustomerAuthenticationFailureHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@Getter
@Setter
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .anyRequest()
                .authenticated()
                .and()
//                .addFilterBefore(beforeAuthenticationFilter,
//                        BeforeAuthenticationFilter.class)
                .addFilterBefore(beforeAuthenticationFilter11(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
//                .successHandler(loginSuccessHandler)
                .failureHandler(customerAuthenticationFailureHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private CustomerAuthenticationFailureHandler customerAuthenticationFailureHandler;



    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public BeforeAuthenticationFilter beforeAuthenticationFilter11() {
        BeforeAuthenticationFilter beforeAuthenticationFilter1 = new BeforeAuthenticationFilter(authenticationManager);
        return beforeAuthenticationFilter1;
    }

}
