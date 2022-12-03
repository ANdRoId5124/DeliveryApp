package com.example.deliveryapp.service;

import com.example.deliveryapp.enteties.Restaurant;
import com.example.deliveryapp.enteties.RestaurantMenu;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, UserService userService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
    }

    public Optional<Restaurant> getRestaurant(Integer id){
        return restaurantRepository.findById(id);
    }
    public Restaurant addRestaurant(User user, Restaurant restaurant){
        restaurantRepository.save(restaurant);
        user.setRestaurant(restaurant);
        userService.updateUser(user);
        return restaurant;
    }

    public Restaurant updateRestaurant(Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Restaurant restaurant){
        restaurantRepository.delete(restaurant);
    }

    public List<Restaurant> getAll(){
        return restaurantRepository.findAll();
    }
}
