package com.example.deliveryapp.controller;


import com.example.deliveryapp.UserHolder.UserHolder;
import com.example.deliveryapp.enteties.Order;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.service.OrderService;
import com.example.deliveryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    private final UserHolder userHolder;
    private final OrderService orderService;

    private final UserService userService;

    @Autowired
    public DeliveryController(UserHolder userHolder, OrderService orderService, UserService userService) {
        this.userHolder = userHolder;
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/change_to_online")
    public void changeStatusToOnline(){
       User delivery = userHolder.getAuthUser();
       delivery.setStatus("online");
       userService.updateUser(delivery);
    }

    @PostMapping("/change_status_to_offline")
    public void changeStatusToOffline(){
        User delivery = userHolder.getAuthUser();
        delivery.setStatus("offline");
        userService.updateUser(delivery);
    }

    @PostMapping("/to_complete_order")
    public void toCompleteOrder(){
        User delivery = userHolder.getAuthUser();
        Order completedOrder = delivery.getOrderBuffer().get(0);
        completedOrder.setStatus("complete");
        orderService.updateOrder(completedOrder);
        delivery.getOrderBuffer().remove(0);
        delivery.setStatus("online");
        userService.updateUser(delivery);
    }
}
