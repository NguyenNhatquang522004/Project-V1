package com.example.my_app.Repository.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_app.Enum.StatusUserEntry;
import com.example.my_app.model.User.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndStatusEntry(String email, StatusUserEntry statusEntry);

    Optional<User> findByUsername(String username);

}
