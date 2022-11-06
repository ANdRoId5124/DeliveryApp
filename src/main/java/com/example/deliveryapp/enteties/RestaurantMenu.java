package com.example.deliveryapp.enteties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "restaurant_menu_table")
public class RestaurantMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer menuId;
    @ManyToMany
    @JoinColumn(name = "food_id")
    private Set<Food> menu;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Set<Food> getMenu() {
        return menu;
    }

    public void setMenu(Set<Food> menu) {
        this.menu = menu;
    }
}
