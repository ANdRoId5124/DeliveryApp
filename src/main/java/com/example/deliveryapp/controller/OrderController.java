package com.example.deliveryapp.controller;

import com.example.deliveryapp.UserHolder.UserHolder;
import com.example.deliveryapp.enteties.*;
import com.example.deliveryapp.priceCalculator.PriceCalculator;
import com.example.deliveryapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/user")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final UserService userService;
    private final FoodService foodService;

    private final RestaurantService restaurantService;
    private final UserHolder userHolder;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService, UserService userService,
                           FoodService foodService, RestaurantService restaurantService,
                           UserHolder userHolder) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.userService = userService;
        this.foodService = foodService;
        this.restaurantService = restaurantService;
        this.userHolder = userHolder;
    }

    @PostMapping("/order/{restaurantId}/{foodId}/{amount}")
    public ResponseEntity<?> makeOrder(@PathVariable Integer restaurantId,
                                       @PathVariable Integer foodId,
                                       @PathVariable int amount) {
//        if (restaurantService.getRestaurant(restaurantId).isPresent()) {
            RestaurantMenu menu = restaurantService.getRestaurant(restaurantId).get().getMenu();
//            if (menu.getMenu().contains(foodService.getFood(foodId))) {
                Food orderedFood = foodService.getFood(foodId).get();
                orderedFood.setAmount(amount);
                orderedFood.setPrice(PriceCalculator.calculateForFood(orderedFood));foodService.updateFood(orderedFood);
                Order madeOrder = orderService.makeOrder(orderedFood);
                madeOrder.setPrice(PriceCalculator.calculateForOrder(madeOrder));
                madeOrder.setDeliveryAddress(userHolder.getAuthUser().getDeliveryAddress());
                orderService.updateOrder(madeOrder);
                User user = userHolder.getAuthUser();
                Cart cart = cartService.addOrderToCart(user, madeOrder);
                user.setCart(cart);
                userService.updateUser(user);
                return new ResponseEntity<>(madeOrder, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        }else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer id) {
        return new ResponseEntity<>(orderService.getOrder(id).get(), HttpStatus.OK);
    }

    @GetMapping("/get_all")
    public Set<Order> getAll() {
        Cart cart = userHolder.getAuthUser().getCart();
        return orderService.getOrders(cart);
    }


    @DeleteMapping("/delete/{orderId}")
    public void deleteOrder(@PathVariable Integer orderId) {
        Order order = orderService.getOrder(orderId).get();
        if (order != null) {
            User user = userHolder.getAuthUser();
            Set<Order> orders = user.getCart().getOrders();
            if (!orders.isEmpty()) {
                if (orders.contains(order)) {
                    orders.remove(order);
                }
            }
        }
    }

    @PutMapping("update_order/add_food/{orderId}/{foodId}/{amount}")
    public ResponseEntity<?> updateOrderAddFood(@PathVariable Integer orderId,
                                                @PathVariable Integer foodId,
                                                @PathVariable int amount) {
        Food addedFood = foodService.getFood(foodId).get();
        if (addedFood != null) {
            addedFood.setAmount(amount);
            addedFood.setPrice(PriceCalculator.calculateForFood(addedFood));
            Order order = orderService.getOrder(orderId).get();
            if (order != null) {
                User user = userHolder.getAuthUser();
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

    @PutMapping("update_order/delete_food/{orderId}/{foodId}")
    public ResponseEntity<?> updateOrderDeleteFood(@PathVariable Integer orderId,
                                                   @PathVariable Integer foodId) {
        Food deletedFood = foodService.getFood(foodId).get();
        if (deletedFood != null) {
            Order order = orderService.getOrder(orderId).get();
            if (order != null) {
                User user = userHolder.getAuthUser();
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

