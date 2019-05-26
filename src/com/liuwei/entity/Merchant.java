package com.liuwei.entity;

/**
 * @ClassName Merchant
 * @Description TODO
 * @Author AthLw
 * @Date 2019/5/24 09:00
 * @Version 1.0
 **/
public class Merchant {
    private int merchantID;
    private String merchantName;
    private String merchantLocation;
    private double allSales;

    public Merchant(int merchantID, String merchantName, String merchantLocation, double allSales) {
        this.merchantID = merchantID;
        this.merchantName = merchantName;
        this.merchantLocation = merchantLocation;
        this.allSales = allSales;
    }

    public void setMerchantID(int merchantID) {
        this.merchantID = merchantID;
    }

    public void setAllSales(double allSales) {
        this.allSales = allSales;
    }

    public int getMerchantID() {
        return merchantID;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantLocation() {
        return merchantLocation;
    }

    public void setMerchantLocation(String merchantLocation) {
        this.merchantLocation = merchantLocation;
    }

    public double getAllSales() {
        return allSales;
    }

    public void addSales(double sales) {
        this.allSales += sales;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "merchantID=" + merchantID +
                ", merchantName='" + merchantName + '\'' +
                ", merchantLocation='" + merchantLocation + '\'' +
                ", allSales=" + allSales +
                '}';
    }
}
