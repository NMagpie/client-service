package com.clientservice.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

public class DHOrder {

    @JsonProperty("order_id")
    private int orderId;

    @JsonProperty("is_ready")
    @Getter
    private boolean isReady;

    @JsonProperty("estimated_waiting_time")
    @Getter
    private int estimatedWaitingTime;

    @JsonProperty("priority")
    private int priority;

    @JsonProperty("max_wait")
    private float maxWait;

    @JsonProperty("created_time")
    private long createdTime;

    @JsonProperty("registered_time")
    private long registeredTime;

    @JsonProperty("cooking_time")
    private int cookingTime;

    @JsonProperty("cooking_details")
    private ArrayList<HashMap<String,Integer>> cookingDetails;

    @JsonCreator
    public DHOrder(@JsonProperty("order_id") int orderId,
                   @JsonProperty("is_ready") boolean isReady,
                   @JsonProperty("estimated_waiting_time") int estimatedWaitingTime,
                   @JsonProperty("priority") int priority,
                   @JsonProperty("max_wait") float maxWait,
                   @JsonProperty("created_time") long createdTime,
                   @JsonProperty("registered_time") long registeredTime,
                   @JsonProperty("cooking_time") int cookingTime,
                   @JsonProperty("cooking_details") ArrayList<HashMap<String, Integer>> cookingDetails) {
        this.orderId = orderId;
        this.isReady = isReady;
        this.estimatedWaitingTime = estimatedWaitingTime;
        this.priority = priority;
        this.maxWait = maxWait;
        this.createdTime = createdTime;
        this.registeredTime = registeredTime;
        this.cookingTime = cookingTime;
        this.cookingDetails = cookingDetails;
    }
}
