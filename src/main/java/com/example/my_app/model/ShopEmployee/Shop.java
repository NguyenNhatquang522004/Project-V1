package com.example.my_app.model.ShopEmployee;

import java.util.UUID;

import com.example.my_app.model.Base.TimeBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Permission")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class Shop extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
}
