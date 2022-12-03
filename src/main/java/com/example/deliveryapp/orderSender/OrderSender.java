package com.example.deliveryapp.orderSender;

import com.example.deliveryapp.OrderHolder.OrderHolder;
import com.example.deliveryapp.enteties.Order;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.onlineDeliveryBoysHolder.OnlineDeliveryHolder;
import com.example.deliveryapp.service.UserService;

public class OrderSender {
    private final OnlineDeliveryHolder onlineDeliveryHolder;
    private final UserService userService;

    public OrderSender(OnlineDeliveryHolder onlineDeliveryHolder, UserService userService) {
        this.onlineDeliveryHolder = onlineDeliveryHolder;
        this.userService = userService;
    }

    public void sendOrders() {
        while (!OrderHolder.getOrdersFromOrdersHolder().isEmpty()) {
            if (!onlineDeliveryHolder.getAllOnlineDelivery().isEmpty()) {
                int theNumberOfOnlineDelivery =
                        (int) (0 + (Math.random() * ((onlineDeliveryHolder
                                .getAllOnlineDelivery()
                                .size() - 1) - 0) + 1));
                User delivery = onlineDeliveryHolder.getAllOnlineDelivery()
                        .get(theNumberOfOnlineDelivery);
                for (Order order : OrderHolder.getOrdersFromOrdersHolder()) {
                    delivery.getOrderBuffer().add(order);
                    delivery.setStatus("busy");
                    userService.updateUser(delivery);
                    onlineDeliveryHolder.getAllOnlineDelivery().remove(delivery);
                    OrderHolder.getOrdersFromOrdersHolder().remove(order);
                }
            }
        }
    }
}
