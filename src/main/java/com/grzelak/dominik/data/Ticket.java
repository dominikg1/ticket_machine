package com.grzelak.dominik.data;

/**
 * Klasa reprezentujaca bilet skladajaca sie z nazwy biletu, jego ceny, opcjonalnej znizki oraz ilosci biletow danego rodzaju
 * */
public class Ticket {
    private String name;
    private int price;
    private double discount;
    private int quantity;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Ticket(String name, int price, double discount, int quantity) {
        this.name = name;
        this.price = (int)Math.round(price*discount/10)*10;
        this.discount = discount;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return getName() + " " + (double)getPrice()/100 + "PLN";
    }
}
