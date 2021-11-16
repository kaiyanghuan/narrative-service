package com.example.narrative.controllers;

import com.example.narrative.authentications.JwtTokenProvider;
import com.example.narrative.authentications.UserContext;
import com.example.narrative.controllers.requests.LoginRequest;
import com.example.narrative.controllers.responses.ApiResponse;
import com.example.narrative.controllers.responses.JwtAuthenticationResponse;
import com.example.narrative.controllers.responses.UserResponse;
import com.example.narrative.services.UserService;
import com.example.narrative.utils.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ResponseHelper responseHelper;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getUsername().isEmpty() || loginRequest.getPassword().isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "Invalid username or password"), HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        String jwtToken = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken));
    }

    @GetMapping("/whoami")
    public ResponseEntity<UserResponse> whoami() {
        return ResponseEntity.ok(responseHelper.from(userService.getUserByName(UserContext.loggedInUsername())).toUserResponse());
    }
}
