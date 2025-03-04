package com.example.my_app.DTO.Role_Permisson_Admin;


import java.util.Set;
import java.util.UUID;


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
public class Admin_PermissonDTO {
 
    UUID id;
    Set<Admin_RoleDTO> admin_Permisson_Role ;
}
