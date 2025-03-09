package com.example.my_app.DTO.Role_Permission;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.DTO.User.UserDTO;
import com.example.my_app.Enum.Role_Permission.StatusRole;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
public class RoleDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;
    @JsonDeserialize(using = AutoDeserializer.class)
    StatusRole description;

    UserDTO role_user;

    Set<PermissionDTO> role_permission;
}
