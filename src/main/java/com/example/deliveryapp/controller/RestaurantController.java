package com.example.deliveryapp.controller;

import com.example.deliveryapp.enteties.Restaurant;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.service.RestaurantService;
import com.example.deliveryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("manager")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }


    @PostMapping("/add/{userId}") // userId for testing
    public Restaurant addRestaurant(@PathVariable Integer userId, @RequestBody Restaurant restaurant) {
        //User user = UserHolder.getAuthUser();  get auth user, when will be added Spring Security
        User user = userService.findById(userId).get(); // user for testing
        return restaurantService.addRestaurant(user, restaurant);
    }

    @DeleteMapping("/delete_restaurant/{userId}") // use AUTH USER
    public void deleteRestaurant(@PathVariable Integer userId){
        User user = userService.findById(userId).get();
        Restaurant restaurantForDelete = user.getRestaurant();
        restaurantService.deleteRestaurant(restaurantForDelete);
        userService.updateUser(user);
    }
}
