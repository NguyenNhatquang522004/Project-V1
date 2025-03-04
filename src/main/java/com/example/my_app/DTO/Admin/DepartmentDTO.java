package com.example.my_app.DTO.Admin;


import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.EnumDeserializer;
import com.example.my_app.DTO.Role_Permisson_Admin.Admin_RoleDTO;
import com.example.my_app.Enum.StatusDepartment;

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
public class DepartmentDTO{

    UUID id;

    @JsonDeserialize(using = EnumDeserializer.class)
    StatusDepartment description;

    Set<Admin_RoleDTO> employee_Attendance;
}
