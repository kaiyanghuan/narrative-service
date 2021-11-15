package com.example.narrative.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", length = 100)
    private String id = UUID.randomUUID().toString();

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "access_code")
    private String accessCode;

    @Column(name = "master_account")
    private String masterAccount;

    @Column(name = "roles")
    private String roles = "ADMIN";

    @Column(name = "permission")
    private String permissions = "ACCESS_USERS";

    public List<String> getRoleList() {
        if (roles.length() == 0) {
            new ArrayList<>();
        }
        return Arrays.asList(roles.split(","));
    }

    public List<String> getPermissionList() {
        if (permissions.length() == 0) {
            new ArrayList<>();
        }
        return Arrays.asList(permissions.split(","));
    }
}
