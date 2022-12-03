package com.example.deliveryapp.courierdentifierForTheOrder;


import com.example.deliveryapp.OrderHolder.OrderHolder;
import com.example.deliveryapp.enteties.Order;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.onlineDeliveryBoysHolder.OnlineDeliveryHolder;
import com.example.deliveryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderSender {
    private final OnlineDeliveryHolder onlineDeliveryHolder;
    private final UserService userService;

    @Autowired
    public OrderSender(OnlineDeliveryHolder onlineDeliveryHolder, UserService userService) {
        this.onlineDeliveryHolder = onlineDeliveryHolder;
        this.userService = userService;
    }

    public void sendOrders() {
        if (!onlineDeliveryHolder.getAllOnlineDelivery().isEmpty()) {
            while (OrderHolder.getOrdersFromOrdersHolder().size() != 0) {
                int theNumberOfDeliveryInHolder =
                        (int) ((Math.random() * (onlineDeliveryHolder
                                .getAllOnlineDelivery()
                                .size() - 1)) + 1);
                User delivery = onlineDeliveryHolder.getAllOnlineDelivery().get(theNumberOfDeliveryInHolder);
                Order orderForDelivery = OrderHolder.getOrdersFromOrdersHolder().get(0);
                delivery.getOrderBuffer().add(orderForDelivery);
                delivery.setStatus("busy");
                userService.updateUser(delivery);
                onlineDeliveryHolder.getAllOnlineDelivery().remove(delivery);
            }
        }
    }
}