package com.duong.ecommerce.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Size {
    private String name;

    private int quantity;

    public Size(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuanity(int quantity) {
        this.quantity = quantity;
    }
}
