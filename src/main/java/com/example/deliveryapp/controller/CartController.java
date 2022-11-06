package com.example.deliveryapp.controller;

import com.example.deliveryapp.UserHolder.UserHolder;
import com.example.deliveryapp.enteties.Cart;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.service.CartService;
import com.example.deliveryapp.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserHolder userHolder;
    private final UserService userService;

    public CartController(CartService cartService, UserHolder userHolder, UserService userService) {
        this.cartService = cartService;
        this.userHolder = userHolder;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Cart getCart(@PathVariable Integer id){ // id need for test
//        User user = userHolder.getAuthUser(); authUser in RELEASE this will be
        User user = userService.findById(id).get(); // for testing
        return user.getCart();
    }


}
