package com.example.my_app.Repository.Order;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_app.Enum.StatusPaymentMethod;
import com.example.my_app.model.Order.Order_Payment;

@Repository
public interface OrderPaymentRepository extends JpaRepository<Order_Payment, UUID> {
    Optional<Order_Payment> findByStatusPaymentMethod(StatusPaymentMethod request);
}
