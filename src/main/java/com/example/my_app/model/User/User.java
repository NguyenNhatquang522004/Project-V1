package com.example.my_app.model.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.my_app.Enum.user.GenderUser;
import com.example.my_app.Enum.user.StatusUserEntry;
import com.example.my_app.model.Base.TimeBase;
import com.example.my_app.model.Order.Order;

import com.example.my_app.model.Role_Permission.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;


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

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class User extends TimeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String username;

    @Column(columnDefinition = "nvarchar(255)", nullable = true, unique = true)
    String email = null;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    GenderUser Gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    StatusUserEntry statusEntry;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String password = null;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String code;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String url;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String accessToken;

    @Column(columnDefinition = "nvarchar(255)", nullable = true)
    String refreshToken;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime code_expired;

    @Column(precision = 10, scale = 2)
    int balance;

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    Set<Address> user_address = new HashSet<>();

    // @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "user_id", orphanRemoval = true)
    // @JsonIgnore
    // Set<OrderStatusHistory> user_orderHistory = new HashSet<>();

    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "Role_id", referencedColumnName = "id")
    Role user_role;

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true, mappedBy = "order_User")
    @JsonIgnore
    Set<Order> user_order = new HashSet<>();


    

}
