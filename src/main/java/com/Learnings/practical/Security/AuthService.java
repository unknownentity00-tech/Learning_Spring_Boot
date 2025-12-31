package com.Learnings.practical.Security;

import com.Learnings.practical.Entity.User;
import com.Learnings.practical.Repositry.UserRepositry;
import com.Learnings.practical.dto.LoginRequestDto;
import com.Learnings.practical.dto.LoginResponseDto;
import com.Learnings.practical.dto.SignupResponseDto;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
       );// AUTHETICATION MENAGER WILL TRY TO AUTHETICATE GOING TO DIFFIRENT MANAGERS
// IF ERROR COME IT WILL NOT PROCED TO NEXT LINE , I PROCEED WE WILL CONVERT IT INOT THE USER
    User user = (User) authentication.getPrincipal();
    // from these user we can sen the jwt token
        String token = authUtil.generateToken(user);

        return new LoginResponseDto(token , user.getId());
    }

    public SignupResponseDto Signup(LoginRequestDto signupRequestDto) {
        Optional<User> user = UserRepositry.findByUsername(signupRequestDto.getUsername());
        if (user.isPresent()) {
            throw new BadCredentialsException("Username already exists" + " " + signupRequestDto.getUsername());
        }
         user = UserRepositry.save(User
                 .builder()
                 .Username(signupRequestDto.getUsername())
                 .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                 .build()
         );
        return modelMapper.map(user.get(), SignupResponseDto.class);
    }
}
