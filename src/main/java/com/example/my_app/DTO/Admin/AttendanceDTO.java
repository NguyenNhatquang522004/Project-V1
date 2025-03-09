package com.example.my_app.DTO.Admin;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.my_app.Configuration.AutoDeserializer;

import com.example.my_app.Enum.StatusAttendance;

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
public class AttendanceDTO {
    @JsonDeserialize(using = AutoDeserializer.class)
    UUID id;

    LocalDateTime workLocalDateTime;

    LocalDateTime check_in;

    LocalDateTime check_out;

    @JsonDeserialize(using = AutoDeserializer.class)
    StatusAttendance status_Attendance;

    EmployeeDTO employee_id;

    Work_ScheduleDTO attendance_Work_Schedule;

}
