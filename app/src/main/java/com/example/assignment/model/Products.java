package com.example.assignment.model;

public class Products {
    private String price;

    private String name;

    private String currency;

    private String url;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Products{" +
                "price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
