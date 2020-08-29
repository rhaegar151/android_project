package com.example.yogeshwar.rentalplatform;

public class book_details {
    String bname;
    int price;
    String murl;
    int nc;


    public book_details() {
    }

    public book_details(String bname, int price, String murl,int nc) {
        this.bname = bname;
        this.price = price;
        this.murl = murl;
        this.nc=nc;

    }

    public int getNc() {
        return nc;
    }

    public void setNc(int nc) {
        this.nc = nc;
    }

    public String getBname() {
        return bname;
    }

    public int getPrice() {
        return price;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMurl() {
        return murl;
    }

    public void setMurl(String murl) {
        this.murl = murl;
    }

    @Override
    public String toString() {
        return "name='" + bname + '\'' +
                ", price=" + price +
                ", murl='" + murl + '\'' +
                ", nc=" + nc +
                '}';
    }
}
