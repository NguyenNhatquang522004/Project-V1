package com.example.my_app.DTO.Role_Permisson_Admin;


import java.util.Set;
import java.util.UUID;

import com.example.my_app.model.Admin.Department;
import com.example.my_app.model.Admin.Employee;




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
public class Admin_RoleDTO {

    UUID id;
    
    Department role_Department;
    Employee role_Employee;
    Set<Admin_PermissonDTO> admin_Role_Permisson;

}
