package com.example.my_app.Repository.Ship;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_app.model.ship.ShipmentInfo;

@Repository
public interface ShipmentInfoRepository extends JpaRepository<ShipmentInfo, UUID> {

}
