package com.example.assignment.model;

import java.util.ArrayList;

public class ProductModel {

    private String title;

    private ArrayList<Conversion> conversion;

    private ArrayList<Products> products;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Conversion> getConversion() {
        return conversion;
    }

    public void setConversion(ArrayList<Conversion> conversion) {
        this.conversion = conversion;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "title='" + title + '\'' +
                ", conversion=" + conversion +
                ", products=" + products +
                '}';
    }
}
