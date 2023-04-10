package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.repository.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
