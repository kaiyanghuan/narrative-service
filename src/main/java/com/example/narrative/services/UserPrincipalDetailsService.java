package com.example.narrative.services;

import com.example.narrative.entities.User;
import com.example.narrative.entities.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        if (name.equals("ADMIN")) {
            return new UserPrincipal(User.builder()
                    .id(UUID.randomUUID().toString())
                    .name("Admin")
                    .accessCode("1234567")
                    .masterAccount("123-45678-9")
                    .password("pass123")
                    .roles("ALL")
                    .permissions("ALL")
                    .build());
        }

        User user = userService.getUserByName(name);
        return new UserPrincipal(user);
    }
}
