package com.clientservice.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {

    @JsonProperty("restaurant_id")
    private int restaurantId;

    @JsonProperty("rating")
    private int rating;

    public Rating(int restaurantId, int rating) {
        this.restaurantId = restaurantId;
        this.rating = rating;
    }

}
