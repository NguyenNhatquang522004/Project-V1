package com.example.my_app.Repository.Role;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_app.model.Role_Permission.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

}
