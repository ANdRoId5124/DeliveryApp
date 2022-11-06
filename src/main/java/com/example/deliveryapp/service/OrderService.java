package com.example.deliveryapp.service;

import com.example.deliveryapp.enteties.Cart;
import com.example.deliveryapp.enteties.Food;
import com.example.deliveryapp.enteties.Order;
import com.example.deliveryapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final FoodService foodService;

    @Autowired
    public OrderService(OrderRepository orderRepository, FoodService foodService) {
        this.orderRepository = orderRepository;
        this.foodService = foodService;
    }


    public Order makeOrder (Food food){
        Order order = new Order();
        Set<Food> orderFoods = new HashSet<>();
        orderFoods.add(food);
        order.setOrder(orderFoods);
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    public void deleteOrder(Integer id){
        orderRepository.deleteById(id);
    }

    public Set<Order> getOrders(Cart cart){
        return cart.getOrders();
    }

    public Optional<Order> getOrder(Integer id){
        return orderRepository.findById(id);
    }

    public Order updateOrder(Order order){
       return orderRepository.save(order);
    }

}
