package com.clientservice.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class ClientOrder {

    @JsonProperty(value = "client_id")
    private int clientId;

    @JsonProperty("orders")
    private ArrayList<Order> orders;

    @JsonCreator
    public ClientOrder(@JsonProperty("order_id") int clientId,
                       @JsonProperty("orders") ArrayList<Order> orders) {
        this.clientId = clientId;
        this.orders = orders;
    }

    public Boolean isEmpty() {
        return orders.isEmpty();
    }

    public void remove(Order order) {
        orders.remove(order);
    }

    public Order minWaitingTime() {

        Optional<Order> order = orders.stream().min(Comparator.comparingInt(Order::getEstimatedWaitingTime));

        return order.orElse(null);
    }

    public void reduceTime(long duration) {
        for (Order order : orders)
            order.reduceEstTime(duration);
    }

    @Override
    public String toString() {
        return "ClientOrder{" +
                "clientId=" + clientId +
                ", orders=" + orders +
                '}';
    }
}
