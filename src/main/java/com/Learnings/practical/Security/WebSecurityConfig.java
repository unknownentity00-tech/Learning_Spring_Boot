package com.Learnings.practical.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private  final PasswordEncoder passwordEncoder;
    private  final  JwtAuthFilter jwtAuthFilter;

@Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
   httpSecurity// for now we were going statless but fro we want that  the token are not stored in server but exceept stored
           // in  the manager that we we will use the below
           .csrf(csrfConfig->csrfConfig.disable())
           .sessionManagement(sessionconfig ->
                   sessionconfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           .authorizeHttpRequests(auth->auth
           .requestMatchers("/public/**","/auth/**").permitAll()
           .requestMatchers("/restraunt/**").hasRole("Restraunt")
           .anyRequest().authenticated()
           )
           .formLogin(Customizer.withDefaults())
           .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class);
   return httpSecurity.build();
  }


    //here the main significance holds is permitAll() that means anyone can access authenticated()
    // means the login page will appear as a autheticator

    //creating in memory role detials
    //@Bean
    UserDetailsService userDetailsService() {
            UserDetails user1 = User
                .withUsername("admin")
                .password(passwordEncoder.encode("pass1"))
                .roles("Restraunt")
                .build();

    // return new InMemoryUserDetailsManager(user1);



        UserDetails  user2 =  User
                .withUsername("user")
                .password(passwordEncoder.encode("pass"))
                // we cannot directly stor the password here in fact we have to
                // encode it thats why we added the password encoder file it can beseen by someone
                .roles("public")
                .build();
        return new InMemoryUserDetailsManager(user1 , user2);
    }
}
