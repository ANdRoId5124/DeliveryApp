package com.example.deliveryapp.priceCalculator;

import com.example.deliveryapp.enteties.Food;
import com.example.deliveryapp.enteties.Order;

import java.util.Set;


public class PriceCalculator {
    public static double calculateForFood(Food food) {
        return food.getPrice() * food.getAmount();
    }

    public static double calculateForOrder(Order order) { // ADD PRICE TO ORDER!!!
        double price = 0;
        Set<Food> foods = order.getOrder();
        Food[] foodsBuffer = foods.toArray(new Food[0]);
        for (Food food : foodsBuffer) {
            price += food.getPrice();
        }
        return price;
    }
}
