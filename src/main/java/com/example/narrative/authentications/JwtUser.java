package com.example.narrative.authentications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

import static java.lang.String.format;

@Data
@Builder
@AllArgsConstructor
public class JwtUser {

    private String username;
    private String accessCode;
    private List<String> roles;
    private Date dateIssued;

    @Override
    public String toString() {
        return format("JwtUser(username=%s, accessCode=%s, dateIssued=%s)", username, accessCode, dateIssued);
    }
}
