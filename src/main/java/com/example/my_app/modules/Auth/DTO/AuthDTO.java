package com.example.my_app.modules.Auth.DTO;

import java.security.Permission;
import java.util.Set;

import com.example.my_app.Enum.StatusRole;

import groovy.transform.builder.Builder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
    String username;
    String password;
    StatusRole role;
    Set<String> permission;

}
