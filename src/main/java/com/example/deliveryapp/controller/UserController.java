package com.example.deliveryapp.controller;

import com.example.deliveryapp.OrderHolder.OrderHolder;
import com.example.deliveryapp.UserHolder.UserHolder;
import com.example.deliveryapp.enteties.Order;
import com.example.deliveryapp.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserHolder userHolder;
    private final FoodService foodService;

    public UserController(UserHolder userHolder, FoodService foodService) {
        this.userHolder = userHolder;
        this.foodService = foodService;
    }

    @PostMapping("/pay/{orderId}")
    public ResponseEntity<?> payForOrder(@PathVariable Integer orderId) {
//        if (!userHolder.getAuthUser().getCart().getOrders().isEmpty()) {
//            if (userHolder.getAuthUser().getCart().getOrders()
//                    .contains(foodService.getFood(orderId))) {
        Set<Order> orders = userHolder.getAuthUser().getCart().getOrders();
        Order[] arrayOfOrders = orders.toArray(new Order[0]);
        for (Order order : arrayOfOrders) {
            if (order.getOrderId() == orderId) {
                OrderHolder.setToOrderHolder(order);
                return new ResponseEntity<>("pay confirmed", HttpStatus.OK);
//                    }
//                }
//            } else return new ResponseEntity<>("order not found", HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>("cart is empty", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
    }
}