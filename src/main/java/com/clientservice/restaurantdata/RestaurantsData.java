package com.clientservice.restaurantdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;

public class RestaurantsData {

    @JsonProperty("restaurants")
    @Getter
    private final int restaurants;

    @JsonProperty("restaurants_data")
    @Getter
    private final ArrayList<Restaurant> restaurantsData;

    public RestaurantsData(@JsonProperty("restaurants") int restaurants,
                           @JsonProperty("restaurants_data") ArrayList<Restaurant> restaurantsData) {
        this.restaurants = restaurants;
        this.restaurantsData = restaurantsData;
    }

    public Restaurant getRestById(int id) {

        for (Restaurant restaurant : restaurantsData)
            if (restaurant.getId() == id) return restaurant;

        return null;
    }

    @Override
    public String toString() {
        return "RestaurantsData{" +
                "restaurants=" + restaurants +
                ", restaurantsData=" + restaurantsData +
                '}';
    }
}