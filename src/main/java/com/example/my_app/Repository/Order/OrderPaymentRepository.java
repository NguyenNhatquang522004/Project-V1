package com.example.my_app.Repository.Order;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_app.model.Order.Order_Payment;
import com.example.my_app.model.Order.Order_WayBill;

@Repository
public interface OrderPaymentRepository extends JpaRepository<Order_Payment, UUID> {

}
