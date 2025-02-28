package com.example.my_app.modules.Auth.DTO;


import java.util.Set;

import com.example.my_app.Enum.Role_Permission.StatusRole;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    StatusRole role;
    Set<String> permission ;

}
