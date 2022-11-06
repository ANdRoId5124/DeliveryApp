package com.example.deliveryapp.controller;

import com.example.deliveryapp.enteties.*;
import com.example.deliveryapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final UserService userService;
    private final FoodService foodService;

    private final RestaurantService restaurantService;
//    private final UserHolder userHolder; in RELEASE

    @Autowired
    public OrderController(OrderService orderService, CartService cartService, UserService userService, FoodService foodService, RestaurantService restaurantService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.userService = userService;
        this.foodService = foodService;
        this.restaurantService = restaurantService;
    }

    @PostMapping("/order/{id}/{foodId}") //id for testing, in future use AUTH USER + id of Restaurant + choose food
    // from menu
    public ResponseEntity<?> makeOrder(@PathVariable Integer id, @PathVariable Integer restaurantId,
                           @PathVariable Integer foodId) {
        if (restaurantService.getRestaurant(restaurantId).isPresent()) {
            RestaurantMenu menu = restaurantService.getRestaurant(restaurantId).get().getMenu();
            if (menu.getMenu().contains(foodId)) {
                Food orderedFood = foodService.getFood(foodId).get();
                Order madeOrder = orderService.makeOrder(orderedFood);
                User user = userService.findById(id).get();
                Cart cart = cartService.addOrderToCart(user, madeOrder);
                user.setCart(cart);
                userService.updateUser(user);
                return new ResponseEntity<>(madeOrder, HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer id) {
        return new ResponseEntity<>(orderService.getOrder(id).get(), HttpStatus.OK);
    }

    @GetMapping("/get_all/{userId}") //User AUTH USER
    public Set<Order> getAll(@PathVariable Integer userId) {
        Cart cart = userService.findById(userId).get().getCart();
        return orderService.getOrders(cart);
    }

    //Do return type ResponseEntity<Order>
    @DeleteMapping("/delete/{userId}/{orderId}") //use AUTH USER
    public void deleteOrder(@PathVariable Integer userId, @PathVariable Integer orderId) {
        Order order = orderService.getOrder(orderId).get();
        if (order != null) {
            User user = userService.findById(userId).get();
            Set<Order> orders = user.getCart().getOrders();
            if (!orders.isEmpty()) {
                if (orders.contains(order)) {
                    orders.remove(order);
                    orderService.deleteOrder(orderId);
                }
            }
        }
    }

    @PutMapping("update_order/add_food/{userId}/{orderId}/{foodId}") // use AUTH USER
    public ResponseEntity<?> updateOrderAddFood(@PathVariable Integer userId, @PathVariable Integer orderId,
                                  @PathVariable Integer foodId) {
        Food addedFood = foodService.getFood(foodId).get();
        if (addedFood != null) {
            Order order = orderService.getOrder(orderId).get();
            if (order != null) {
                User user = userService.findById(userId).get();
                Set<Order> orders = user.getCart().getOrders();
                if (!orders.isEmpty()) {
                    if (orders.contains(order)) {
                        order.getOrder().add(addedFood);
                        return new ResponseEntity<>(orderService.updateOrder(order), HttpStatus.OK);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("update_order/delete_food/{userId}/{orderId}/{foodId}") // use AUTH USER
    public ResponseEntity<?> updateOrderDeleteFood(@PathVariable Integer userId, @PathVariable Integer orderId,
                                         @PathVariable Integer foodId) {
        Food deletedFood = foodService.getFood(foodId).get();
        if (deletedFood != null) {
            Order order = orderService.getOrder(orderId).get();
            if (order != null) {
                User user = userService.findById(userId).get();
                Set<Order> orders = user.getCart().getOrders();
                if (!orders.isEmpty()) {
                    if (orders.contains(order)) {
                        order.getOrder().remove(deletedFood);
                        return new ResponseEntity<>(orderService.updateOrder(order), HttpStatus.OK);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}