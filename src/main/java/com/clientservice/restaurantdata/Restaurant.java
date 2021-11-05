package com.clientservice.restaurantdata;

import com.clientservice.restaurantdata.foods.Foods;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;

public class Restaurant {

    @Getter
    private final int id;

    private final String name;

    @Getter
    private final String address;

    @Getter
    private final int menuItems;

    @Getter
    private final ArrayList<Foods> menu;

    private final float rating;

    @JsonCreator
    public Restaurant(@JsonProperty("restaurant_id") int id,
                      @JsonProperty("name") String name,
                      @JsonProperty("address") String address,
                      @JsonProperty("menu_items") int menuItems,
                      @JsonProperty("menu") ArrayList<Foods> menu,
                      @JsonProperty("rating") float rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.menuItems = menuItems;
        this.menu = menu;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", menuItems=" + menuItems +
                ", menu=" + menu +
                ", rating=" + rating +
                '}';
    }
}
