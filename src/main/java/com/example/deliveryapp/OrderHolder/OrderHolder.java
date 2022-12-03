package com.example.deliveryapp.OrderHolder;

import com.example.deliveryapp.enteties.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderHolder {
    private static List<Order> orders = new ArrayList<>();

    public static void setToOrderHolder(Order order) {
        if (order.getStatus().equals("not finished")) {
            orders.add(order);
        }
    }

    public static List<Order> getOrdersFromOrdersHolder(){
        return orders;
    }
}
