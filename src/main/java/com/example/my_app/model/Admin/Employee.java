package com.example.my_app.model.Admin;

import com.example.my_app.Enum.Role_Permission.StatusRole;
import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Role_Permisson_Admin.Admin_Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Employee")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Employee extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String name;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String email;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String phone;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String position;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Country;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Province;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String City;
    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String Ward;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String code;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String accessToken;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String refreshToken;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime code_expired;
    String password;

    StatusRole statusRole;
    // @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    // @JoinTable(name = "Employee_Schedule", joinColumns = @JoinColumn(name =
    // "Employee_id"), inverseJoinColumns = @JoinColumn(name = "Schedule_id"))
    // Set<Work_Schedule> employee_Schedule = new HashSet<>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "role_Employee")
    @JsonIgnore
    Set<Admin_Role> employee_Role = new HashSet<>();

}