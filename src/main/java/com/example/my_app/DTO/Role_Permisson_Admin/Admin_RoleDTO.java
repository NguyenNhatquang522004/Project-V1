package com.example.my_app.DTO.Role_Permisson_Admin;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
import com.example.my_app.DTO.Admin.DepartmentDTO;
import com.example.my_app.DTO.Admin.EmployeeDTO;
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
public class Admin_RoleDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    DepartmentDTO role_Department;
    EmployeeDTO role_Employee;
    Set<Admin_PermissonDTO> admin_Role_Permisson;

}
