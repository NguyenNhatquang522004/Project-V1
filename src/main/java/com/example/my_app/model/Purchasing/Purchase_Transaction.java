package com.example.my_app.model.Purchasing;

import java.util.UUID;

import com.example.my_app.Enum.StatusDepartment;
import com.example.my_app.Enum.StatusPurchasing;
import com.example.my_app.Enum.StatusUserEntry;
import com.example.my_app.model.Base.TimeBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "Purchase_Transaction")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Purchase_Transaction extends TimeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    StatusPurchasing description;

}
