package com.example.deliveryapp.controller;


import com.example.deliveryapp.UserHolder.UserHolder;
import com.example.deliveryapp.enteties.Food;
import com.example.deliveryapp.enteties.Restaurant;
import com.example.deliveryapp.enteties.RestaurantMenu;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.service.FoodService;
import com.example.deliveryapp.service.RestaurantMenuService;
import com.example.deliveryapp.service.RestaurantService;
import com.example.deliveryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

@RestController
public class RestaurantMenuController {
    private final RestaurantMenuService restaurantMenuService;
    private final UserService userService;

    private final FoodService foodService;
    private final RestaurantService restaurantService;

    private final UserHolder userHolder;

    @Autowired
    public RestaurantMenuController(RestaurantMenuService restaurantMenuService, UserService userService, FoodService foodService, RestaurantService restaurantService, UserHolder userHolder) {
        this.restaurantMenuService = restaurantMenuService;
        this.userService = userService;
        this.foodService = foodService;
        this.restaurantService = restaurantService;
        this.userHolder = userHolder;
    }

    @GetMapping("/get_menu/{restaurantId}")
    public ResponseEntity<?> getMenuForUser(@PathVariable Integer restaurantId) {
        String responseForNotFound = "Sorry we don't have menu now";
        if (restaurantService.getRestaurant(restaurantId).get().getMenu() != null) {
            Set<Food> restaurantMenu = restaurantService.getRestaurant(restaurantId).get().getMenu().getMenu();
            if (restaurantMenu == null) {
                return new ResponseEntity<>(responseForNotFound, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(restaurantMenu, HttpStatus.OK);
        } else
            return new ResponseEntity<>(responseForNotFound, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/manager/get_menu")
    public RestaurantMenu getMenu() {
        User user = userHolder.getAuthUser();
        return user.getRestaurant().getMenu();
    }

    @PostMapping("/manager/add_menu")
    public RestaurantMenu addMenu(@RequestBody RestaurantMenu restaurantMenu) {
        User user = userHolder.getAuthUser();
        return restaurantMenuService.createMenu(user, restaurantMenu);
    }

    @PutMapping("/manager/update_menu/add_item")
    public RestaurantMenu updateMenuAddItem(@RequestBody Food food) {
        User user = userHolder.getAuthUser();
        Restaurant restaurant = user.getRestaurant();
        RestaurantMenu menu = restaurant.getMenu();
        if (menu.getMenu() != null) {
            foodService.addFood(food);
            menu.getMenu().add(food);
        } else {
            menu.setMenu(new HashSet<>());
            foodService.addFood(food);
            menu.getMenu().add(food);
        }
        restaurantMenuService.update(menu);
        restaurantService.updateRestaurant(restaurant);
        userService.updateUser(user);
        return menu;
    }

    // Do more if!!!
    @PutMapping("/manager/update_menu/delete_item/{foodId}")
    public RestaurantMenu updateMenuDeleteItem(@PathVariable Integer foodId) {
        User user = userHolder.getAuthUser();
        Restaurant usersRestaurant = user.getRestaurant();
        if (usersRestaurant != null) {
            RestaurantMenu menu = usersRestaurant.getMenu();
            if (menu != null) {
                Set<Food> foods = menu.getMenu();
                if (foods != null) {
                    if (!foods.isEmpty()) {
                        Food foodForRemove = foodService.getFood(foodId).get(); //here IF !!
                        foods.remove(foodForRemove);
                        restaurantMenuService.update(menu);
                    }
                }
            }
        }
        return user.getRestaurant().getMenu();
    }
}