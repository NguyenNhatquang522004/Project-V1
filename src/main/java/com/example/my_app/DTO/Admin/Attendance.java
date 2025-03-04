package com.example.my_app.DTO.Admin;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.my_app.Configuration.EnumDeserializer;
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
public class Attendance {

    UUID id;

    LocalDateTime workLocalDateTime;

    LocalDateTime check_in;

    LocalDateTime check_out;

    @JsonDeserialize(using = EnumDeserializer.class)
    StatusAttendance status_Attendance;

    Employee employee_id;

    Work_Schedule attendance_Work_Schedule;

}
