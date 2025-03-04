package com.example.my_app.DTO.Admin;


import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.EnumDeserializer;
import com.example.my_app.Enum.StatusDepartment;
import com.example.my_app.model.Role_Permisson_Admin.Admin_Role;
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
public class Department{

    UUID id;

    @JsonDeserialize(using = EnumDeserializer.class)
    StatusDepartment description;

    Set<Admin_Role> employee_Attendance;
}
