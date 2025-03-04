package com.example.my_app.DTO.Admin;

import java.time.LocalDateTime;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.EnumDeserializer;
import com.example.my_app.Enum.StatusTimeOfDay;
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
public class Work_Schedule {

    UUID id;

    @JsonDeserialize(using = EnumDeserializer.class)
    StatusTimeOfDay statusTimeOfDay;

    LocalDateTime workLocalDateTime;
    Set<Employee> schedule_Employee;
    Shifts work_Schedule_Shifts;
    Attendance work_Schedule_Attendance;
}
