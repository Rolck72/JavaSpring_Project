package com.example.myproject.services;


import com.example.myproject.models.Order;
import com.example.myproject.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Autowired
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }


    // Удалить заказ
    @Transactional
    public void deleteOrder(int id){
        orderRepository.deleteById(id);
    }

   // Поиск по символам

    @Transactional
    public List findByLastFourCharacters(String s){
        List<Order> orders = orderRepository.findByLastFourCharacters(s);
        return orders;
    }




}
