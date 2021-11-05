package com.clientservice.restaurantdata.foods;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class Foods {

    @JsonProperty("id")
    private final int id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("preparation_time")
    @Getter
    private final int preparation_time;

    @JsonProperty("complexity")
    private final int complexity;

    @JsonProperty("cooking-apparatus")
    private final String cooking_apparatus;

    @JsonCreator
    public Foods(@JsonProperty("id") int id,
                 @JsonProperty("name") String name,
                 @JsonProperty("preparation_time") int preparation_time,
                 @JsonProperty("complexity") int complexity,
                 @JsonProperty("cooking-apparatus") String cooking_apparatus) {
        this.id = id;
        this.name = name;
        this.preparation_time = preparation_time;
        this.complexity = complexity;
        this.cooking_apparatus = cooking_apparatus;

    }
}
