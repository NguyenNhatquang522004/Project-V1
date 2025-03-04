package com.example.my_app.modules.Auth.DTO;

import java.util.Set;

import com.example.my_app.model.Role_Permission.Permission;

import lombok.Builder;
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
public class RoleDTO {
    String description;
    Set<Permission> permission;

}
