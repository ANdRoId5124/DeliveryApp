package com.example.deliveryapp.enteties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "restaurant_table")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantId;

    private String name;
    @OneToOne
    @JoinColumn(name = "menu_id")
    private RestaurantMenu menu;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RestaurantMenu getMenu() {
        return menu;
    }

    public void setMenu(RestaurantMenu menu) {
        this.menu = menu;
    }


}
