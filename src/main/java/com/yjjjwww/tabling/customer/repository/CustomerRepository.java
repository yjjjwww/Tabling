package com.yjjjwww.tabling.customer.repository;

import com.yjjjwww.tabling.customer.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserId(String userId);
}
