package com.example.deliveryapp.controller;

import com.example.deliveryapp.UserHolder.UserHolder;
import com.example.deliveryapp.enteties.Restaurant;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.service.RestaurantService;
import com.example.deliveryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    private final UserHolder userHolder;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, UserService userService, UserHolder userHolder) {
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.userHolder = userHolder;
    }


    @PostMapping("/manager/add")
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        User user = userHolder.getAuthUser();
        return restaurantService.addRestaurant(user, restaurant);
    }

    @DeleteMapping("/manager/delete_restaurant")
    public void deleteRestaurant(){
        User user = userHolder.getAuthUser();
        Restaurant restaurantForDelete = user.getRestaurant();
        restaurantForDelete.setStatus("close");
        restaurantService.updateRestaurant(restaurantForDelete);
        userService.updateUser(user);
    }

    @GetMapping("/restaurants_list")
    public ResponseEntity<?> getAllRestaurants(){
        List<Restaurant> restaurants = restaurantService.getAll();
        List<Restaurant> openRestaurants = new ArrayList<>();
        for(Restaurant restaurant : restaurants){
            if(restaurant.getStatus().contains("open")){
                openRestaurants.add(restaurant);
            }
        }
        String responseForNotFound = "Sorry we don't have restaurant's with menu now";
        if(openRestaurants == null){
            return new ResponseEntity<>(responseForNotFound, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }
}
