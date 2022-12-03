package com.example.deliveryapp.enteties;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "order_table2")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @ManyToMany
    @JoinColumn(name = "order_id")
    private Set<Food> order;

    @Column
    private String deliveryAddress;
    @Column
    private double price;

    @Column
    private String status = "not finished";

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Set<Food> getOrder() {
        return order;
    }

    public void setOrder(Set<Food> order) {
        this.order = order;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
