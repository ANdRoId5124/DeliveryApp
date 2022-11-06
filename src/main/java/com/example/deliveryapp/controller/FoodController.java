package com.example.deliveryapp.controller;

import com.example.deliveryapp.enteties.Food;
import com.example.deliveryapp.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food")
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/add")
    public Food addFood(@RequestBody Food food){
      return foodService.addFood(food);
    }

}
