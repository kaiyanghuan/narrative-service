package com.example.narrative.authentications;

import com.example.narrative.entities.User;
import com.example.narrative.services.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class JwtToSpringAuthenticationBuilder {

    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    public JwtToSpringAuthenticationBuilder(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    private HttpServletRequest request;
    private String token;

    public JwtToSpringAuthenticationBuilder withHttpServletRequest(HttpServletRequest request) {
        this.request = request;
        return this;
    }


    public JwtToSpringAuthenticationBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public Authentication build() {
        Claims claims = jwtTokenProvider.getClaims(token);
        String username = claims.getSubject();
        List<String> roles = (List<String>) claims.get("roles");
        List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        JwtUser jwt = createAuthenticatedDetailsFor(username, claims);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwt, null, authorities);
        WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
        authenticationToken.setDetails(webAuthenticationDetailsSource.buildDetails(request));

        return authenticationToken;
    }

    private JwtUser createAuthenticatedDetailsFor(String name, Claims claims) {
        User user = userService.getUserByName(name);
        return JwtUser.builder()
                .username(user.getName())
                .accessCode(user.getAccessCode())
                .roles((List<String>) claims.get("roles"))
                .dateIssued(claims.getIssuedAt())
                .build();
    }
}
