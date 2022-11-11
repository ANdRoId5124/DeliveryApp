package com.example.deliveryapp.controller;


import com.example.deliveryapp.enteties.Food;
import com.example.deliveryapp.enteties.Restaurant;
import com.example.deliveryapp.enteties.RestaurantMenu;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.service.FoodService;
import com.example.deliveryapp.service.RestaurantMenuService;
import com.example.deliveryapp.service.RestaurantService;
import com.example.deliveryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/manager/menu")
public class RestaurantMenuController {
    private final RestaurantMenuService restaurantMenuService;
    private final UserService userService;

    private final FoodService foodService;
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantMenuController(RestaurantMenuService restaurantMenuService, UserService userService, FoodService foodService, RestaurantService restaurantService) {
        this.restaurantMenuService = restaurantMenuService;
        this.userService = userService;
        this.foodService = foodService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/get_menu/{userId}") // for test
    public RestaurantMenu getMenu(@PathVariable Integer userId){
        User user = userService.findById(userId).get();
        return user.getRestaurant().getMenu();
    }

    @PostMapping("/add_menu/{userId}") //userId for testing, in future will use AUTH USER
    public RestaurantMenu addMenu(@PathVariable Integer userId,
                                  @RequestBody RestaurantMenu restaurantMenu) {
        //User user = userHolder.getAuthUser(); get auth user, when will be added Spring Security
        User user = userService.findById(userId).get();
        return restaurantMenuService.createMenu(user, restaurantMenu);
    }

    @PutMapping("/update_menu/add_item/{userId}")
    public RestaurantMenu updateMenuAddItem(@PathVariable Integer userId,
                                            @RequestBody Food food) {
        User user = userService.findById(userId).get();
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
    @PutMapping("/update_menu/delete_item/{userId}/{foodId}")
    public RestaurantMenu updateMenuDeleteItem(@PathVariable Integer userId,
                                               @PathVariable Integer foodId) {
        User user = userService.findById(userId).get();
        Restaurant usersRestaurant = user.getRestaurant();
        if (usersRestaurant != null) {
            RestaurantMenu menu = usersRestaurant.getMenu();
            if (menu != null) {
                Set<Food> foods = menu.getMenu();
                if(foods != null){
                    if(!foods.isEmpty()){
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