package com.Learnings.practical.Controller;

import com.Learnings.practical.Security.AuthService;
import com.Learnings.practical.dto.LoginRequestDto;
import com.Learnings.practical.dto.LoginResponseDto;
import com.Learnings.practical.dto.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

private final AuthService authService;

@PostMapping
    public ResponseEntity<LoginResponseDto>  login(@RequestBody LoginRequestDto loginRequestDto){
    return  ResponseEntity.ok(authService.login(loginRequestDto));
}



    @PostMapping
    public ResponseEntity<SignupResponseDto>  Signup(@RequestBody LoginRequestDto SignupRequestDto){
        return  ResponseEntity.ok(authService.Signup(SignupRequestDto));
    }

}
