package com.example.deliveryapp.repository;

import com.example.deliveryapp.enteties.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Integer> {
}
