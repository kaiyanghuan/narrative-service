package com.example.narrative.authentications;

import com.example.narrative.exceptions.UnknownAuthenticationPrincipalException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.String.format;

public class UserContext {

    public static JwtUser loggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof JwtUser) {
                return (JwtUser) authentication.getPrincipal();
            }
            throw new UnknownAuthenticationPrincipalException(format("Trying to obtain JwtUser or JwtApiClient but Authentication Principal is %s",
                    principal.getClass().getName()));
        }
        return JwtUser.builder()
                .username("SYSTEM")
                .accessCode("ACCESS_CODE")
                .dateIssued(new Date())
                .roles(new ArrayList<>())
                .build();
    }

    public static String loggedInUsername() throws UnknownAuthenticationPrincipalException {
        JwtUser principal = loggedInUser();
        if (principal instanceof JwtUser) {
            JwtUser jwtUser = (JwtUser) principal;
            return jwtUser.getUsername();
        }
        return "No Logged User";
    }
}
