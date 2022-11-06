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
}
