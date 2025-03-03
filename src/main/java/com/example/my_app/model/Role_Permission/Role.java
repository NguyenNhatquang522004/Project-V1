package com.example.my_app.model.Role_Permission;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.Enum.Role_Permission.StatusRole;
import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.User.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Role")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Role extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    StatusRole description;

    @OneToOne(mappedBy = "user_role", fetch = FetchType.EAGER)
    User role_user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL } )
    @JoinTable(name = "Role_Permission", joinColumns = @JoinColumn(name = "Role_id"), inverseJoinColumns = @JoinColumn(name = "Permission_id"))
    Set<Permission> role_permission = new HashSet<>();
}
