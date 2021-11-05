package com.clientservice.orders;

import com.clientservice.restaurantdata.Restaurant;
import com.clientservice.restaurantdata.foods.Foods;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Order {

    @JsonProperty("restaurant_id")
    @Getter
    private int restaurantId;

    @JsonProperty("items")
    private ArrayList<Integer> items = new ArrayList<>();

    @JsonProperty("priority")
    private int priority;

    @JsonProperty("max_wait")
    private double maxWait = 0;

    @JsonProperty("created_time")
    private long createdTime = System.currentTimeMillis() / 1000L;

    @JsonProperty(value = "restaurant_address", access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    private String address;

    @JsonProperty(value = "order_id", access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    private int orderId;

    @JsonProperty(value = "estimated_waiting_time", access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    @Setter
    private int estimatedWaitingTime = 0;

    @JsonProperty(value = "registered_time", access = JsonProperty.Access.WRITE_ONLY)
    private long registeredTime;

    public Order(Restaurant restaurant) {

        this.restaurantId = restaurant.getId();

        ArrayList<Foods> menu = restaurant.getMenu();

        int numberOfItems = (int) (Math.random() * 5 + 1);

        while (numberOfItems > 0) {
            items.add((int) (Math.random() * restaurant.getMenuItems() + 1));
            numberOfItems--;
        }

        this.priority = (int) (Math.random() * 5 + 1);

        for (Integer item : items)
            if (menu.get(item - 1).getPreparation_time() > maxWait) maxWait = menu.get(item - 1).getPreparation_time();

        maxWait = 1.3 * maxWait;

    }

    @JsonCreator
    public Order(@JsonProperty("restaurant_id") int restaurantId,
                 @JsonProperty("restaurant_address") String address,
                 @JsonProperty("order_id") int orderId,
                 @JsonProperty("estimated_waiting_time") int estimatedWaitingTime,
                 @JsonProperty("created_time") long createdTime,
                 @JsonProperty("registered_time") long registeredTime) {
        this.restaurantId = restaurantId;
        this.address = address;
        this.orderId = orderId;
        this.estimatedWaitingTime = estimatedWaitingTime;
        this.createdTime = createdTime;
        this.registeredTime = registeredTime;
    }

    public void reduceEstTime(long duration) {
        this.estimatedWaitingTime-= duration;
    }

    @Override
    public String toString() {
        return "Order{" +
                "restaurantId=" + restaurantId +
                ", createdTime=" + createdTime +
                ", address='" + address + '\'' +
                ", orderId=" + orderId +
                ", estimatedWaitingTime=" + estimatedWaitingTime +
                ", registeredTime=" + registeredTime +
                '}';
    }
}
