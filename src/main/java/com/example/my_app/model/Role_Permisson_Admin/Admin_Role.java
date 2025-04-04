package com.example.my_app.model.Role_Permisson_Admin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.model.Admin.Department;
import com.example.my_app.model.Admin.Employee;
import com.example.my_app.model.Base.TimeBase;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

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
@Table(name = "Admin_Role")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Admin_Role extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    Department role_Department;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    Employee role_Employee;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(name = "Admin_Role_Permisson", joinColumns = @JoinColumn(name = "Admin_Role_id"), inverseJoinColumns = @JoinColumn(name = "Admin_Permission_id"))
    Set<Admin_Permisson> admin_Role_Permisson = new HashSet<>();

}
