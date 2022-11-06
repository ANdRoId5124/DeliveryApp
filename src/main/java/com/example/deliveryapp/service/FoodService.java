package com.example.deliveryapp.service;

import com.example.deliveryapp.enteties.Food;
import com.example.deliveryapp.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Food addFood(Food food){
        return foodRepository.save(food);
    }

    public void deleteFoodFromFoodList(Integer id){
        foodRepository.deleteById(id);
    }

    public Food updateFoodOnFoodList(Food food){
        return foodRepository.save(food);
    }

    public Optional<Food> getFood(Integer id){
        return foodRepository.findById(id);
    }

    public Food updateFood(Food food){
        return foodRepository.save(food);
    }
}
