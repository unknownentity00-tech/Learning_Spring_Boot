package com.Learnings.practical.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

@Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
   httpSecurity
           .authorizeHttpRequests(auth->auth
                   .requestMatchers("/public /**").permitAll()
                   .requestMatchers("/restraunt/**").authenticated()
           )
           .formLogin(Customizer.withDefaults());
   return httpSecurity.build();
  }
//here the main significance holds is permitAll() that means anyone can access authenticated()
// means the login page will apear  as a auhteticator

}
