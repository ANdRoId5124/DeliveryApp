package com.example.deliveryapp.enteties;

import javax.persistence.*;

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

    private String status = "open";
    public Integer getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
