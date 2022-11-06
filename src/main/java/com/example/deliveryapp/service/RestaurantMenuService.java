package com.example.deliveryapp.service;

import com.example.deliveryapp.enteties.Food;
import com.example.deliveryapp.enteties.Restaurant;
import com.example.deliveryapp.enteties.RestaurantMenu;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.repository.RestaurantMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

@Service
public class RestaurantMenuService {
    private final RestaurantMenuRepository restaurantMenuRepository;
    private final RestaurantService restaurantService;
    private final UserService userService;

    private final FoodService foodService;

    @Autowired
    public RestaurantMenuService(RestaurantMenuRepository restaurantMenuRepository, RestaurantService restaurantService, UserService userService, FoodService foodService) {
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.foodService = foodService;
    }

    public RestaurantMenu createMenu(User user, RestaurantMenu menu){
        Restaurant restaurant = user.getRestaurant();
        restaurantMenuRepository.save(menu);
        restaurant.setMenu(menu);
        restaurantService.updateRestaurant(restaurant);
        userService.updateUser(user);
        return menu;
    }


    public RestaurantMenu update(RestaurantMenu menu){ //use Set<Food instead of food
       return restaurantMenuRepository.save(menu);
    }

//    public RestaurantMenu changeMenu(Integer id, Set<Food> menu){
//        User user = userService.findById(id).get();
//        Restaurant restaurant = user.getRestaurant();
//        RestaurantMenu rMenu = restaurant.getMenu();
//        if(rMenu.getMenu()!=null){
//            rMenu.setMenu(menu);
//        }else{
//            rMenu.setMenu(menu);
//        }
//        restaurantMenuRepository.save(rMenu);
//        restaurantService.updateRestaurant(restaurant);
//        userService.updateUser(user);
//        return rMenu;
//    }
}
