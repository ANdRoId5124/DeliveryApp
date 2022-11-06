package com.example.deliveryapp.service;


import com.example.deliveryapp.enteties.Cart;
import com.example.deliveryapp.enteties.Order;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart createCart(User user){
        Cart cart = new Cart();
        Set<Order> orders = new HashSet<>();
        cart.setOrders(orders);
        user.setCart(cart);
        return cartRepository.save(cart);
    }

    public Cart getCart(User user){
        return user.getCart();
    }

    public Cart addOrderToCart(User user, Order order){
        Cart cart = user.getCart();
        Set<Order> cartOrders = cart.getOrders();
        cartOrders.add(order);
        return cartRepository.save(cart);
    }
}
