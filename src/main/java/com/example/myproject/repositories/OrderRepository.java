package com.example.myproject.repositories;

import com.example.myproject.models.Order;
import com.example.myproject.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// репозиторий заказов
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByPerson(Person person);

    @Query(value = "select * from order where (lower(number)) LIKE%?1", nativeQuery = true)
    List<Order> findByLastFourCharacters (String s);


}
