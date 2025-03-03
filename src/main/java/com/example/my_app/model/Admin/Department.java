package com.example.my_app.model.Admin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.Enum.StatusDepartment;
import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Role_Permisson_Admin.Admin_Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@Table(name = "Department")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Department extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    StatusDepartment description;

    @OneToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.ALL }, orphanRemoval = true, mappedBy = "role_Department")
    Set<Admin_Role> employee_Attendance = new HashSet<>();
}
