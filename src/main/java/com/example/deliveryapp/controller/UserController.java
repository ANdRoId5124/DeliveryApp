package com.example.deliveryapp.controller;


import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.service.CartService;
import com.example.deliveryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public UserController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping("/register")
    public User registration(@RequestBody User user){
        cartService.createCart(user);
        return userService.register(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id){
        return userService.findById(id).get();
    }
}
