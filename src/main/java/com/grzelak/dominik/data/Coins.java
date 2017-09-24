package com.grzelak.dominik.data;

public enum Coins { //typ wyliczeniowy enum definiujacy monety, ktore przyjmuje automat oraz ich poczatkowa ilosc
    ZL_5(500, 5),
    ZL_2(200, 5),
    ZL_1(100, 5),
    GR_50(50, 5),
    GR_20(20, 5),
    GR_10(10, 5);

    private int pln;
    private int amount;

    public int getPln() {
        return pln;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    Coins(int pln, int amount) {
        this.pln = pln;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Coins{" +
                "pln=" + pln +
                ", amount=" + amount +
                '}';
    }
}
