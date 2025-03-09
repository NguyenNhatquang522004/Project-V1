package com.example.my_app.DTO.Admin;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
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
public class DepartmentDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    @JsonDeserialize(using = AutoDeserializer.class)
    StatusDepartment description;

    Set<Admin_RoleDTO> employee_Attendance;
}
