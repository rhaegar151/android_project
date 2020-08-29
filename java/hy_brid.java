package com.example.yogeshwar.rentalplatform;

public class hy_brid {
    book_details b;
    shopdetails s;

    public hy_brid() {
        //empty constructor
    }

    public hy_brid(book_details b, shopdetails s) {
        this.b = b;
        this.s = s;
    }

    public book_details getB() {
        return b;
    }

    public void setB(book_details b) {
        this.b = b;
    }

    public shopdetails getS() {
        return s;
    }

    public void setS(shopdetails s) {
        this.s = s;
    }
}
