package com.example.my_app.DTO.Admin;

import java.time.LocalTime;
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
// ca làm việc
public class ShiftsDTO {

    UUID id;

    @JsonDeserialize(using = EnumDeserializer.class)
    StatusTimeOfDay name;

    LocalTime startTime;

    LocalTime endTime;

    Set<Work_ScheduleDTO> shifts_Work_Schedule ;
}
