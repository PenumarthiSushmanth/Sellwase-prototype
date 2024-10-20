package com.sellwase.Sellwase.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Filters {

    private int minPrice;
    private int maxPrice;
    private String category;
    @JsonProperty("isGiveAway")
    private boolean isGiveAway;
    @JsonProperty("isDelivery")
    private boolean isDelivery;

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isGiveAway() {
        return isGiveAway;
    }

    public void setGiveAway(boolean giveAway) {
        isGiveAway = giveAway;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }

}
