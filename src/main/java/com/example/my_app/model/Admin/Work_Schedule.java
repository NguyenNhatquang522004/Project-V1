package com.example.my_app.model.Admin;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.Enum.StatusTimeOfDay;
import com.example.my_app.model.Base.TimeBase;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Work_Schedule")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Work_Schedule extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Enumerated(EnumType.STRING)
    StatusTimeOfDay statusTimeOfDay;

    LocalDateTime workLocalDateTime;

    @ManyToMany(mappedBy = "employee_Schedule", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    Set<Employee> schedule_Employee = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Shifts_id", nullable = false)
    @JsonIgnore
    Shifts work_Schedule_Shifts;

    @OneToOne(mappedBy = "attendance_Work_Schedule", fetch = FetchType.EAGER)
    Attendance work_Schedule_Attendance;
}
