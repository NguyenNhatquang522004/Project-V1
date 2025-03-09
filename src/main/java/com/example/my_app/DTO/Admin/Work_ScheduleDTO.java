package com.example.my_app.DTO.Admin;

import java.time.LocalDateTime;

import java.util.Set;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;
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
public class Work_ScheduleDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    @JsonDeserialize(using = AutoDeserializer.class)
    StatusTimeOfDay statusTimeOfDay;

    LocalDateTime workLocalDateTime;
    Set<EmployeeDTO> schedule_Employee;
    ShiftsDTO work_Schedule_Shifts;
    AttendanceDTO work_Schedule_Attendance;
}
