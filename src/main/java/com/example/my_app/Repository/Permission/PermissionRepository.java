package com.example.my_app.Repository.Permission;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_app.model.Role_Permission.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

}
