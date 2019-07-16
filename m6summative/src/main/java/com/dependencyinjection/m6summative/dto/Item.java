package com.dependencyinjection.m6summative.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Item {
    @NotNull
    private int itemId;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private double dailyRate;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getItemId() == item.getItemId() &&
                Double.compare(item.getDailyRate(), getDailyRate()) == 0 &&
                getName().equals(item.getName()) &&
                getDescription().equals(item.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId(), getName(), getDescription(), getDailyRate());
    }
}
