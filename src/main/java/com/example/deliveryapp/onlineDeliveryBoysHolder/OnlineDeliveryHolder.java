package com.example.deliveryapp.onlineDeliveryBoysHolder;

import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OnlineDeliveryHolder {

    private final UserService userService;

    @Autowired
    public OnlineDeliveryHolder(UserService userService) {
        this.userService = userService;
    }

    public List<User> getAllOnlineDelivery(){
        List<User> onlineUsers = new ArrayList<>();
        for (User user : userService.getAll()){
            if(user.getStatus().equals("online")){
                onlineUsers.add(user);
            }
        }
        return onlineUsers;
    }
}
