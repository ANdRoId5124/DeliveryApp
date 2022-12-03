package com.example.deliveryapp.UserHolder;

import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {
    private final UserService userService;

    @Autowired
    public UserHolder(UserService userService) {
        this.userService = userService;
    }

    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return userService.findByLogin(name);
    }
}