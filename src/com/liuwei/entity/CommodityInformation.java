package com.liuwei.entity;

/**
 * @ClassName CommodityInformation
 * @Description TODO
 * @Author AthLw
 * @Date 09:00 2019/5/30
 * @Version 1.0
 **/
public class CommodityInformation {
    private String cName;
    private String cDescription;
    private double cPrice;
    private int belongMerchant;

    public CommodityInformation(String cName, String cDescription, double cPrice, int belongMerchant) {
        this.cName = cName;
        this.cDescription = cDescription;
        this.cPrice = cPrice;
        this.belongMerchant = belongMerchant;
    }

    public CommodityInformation() {
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcDescription() {
        return cDescription;
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription;
    }

    public double getcPrice() {
        return cPrice;
    }

    public void setcPrice(double cPrice) {
        this.cPrice = cPrice;
    }

    public int getBelongMerchant() {
        return belongMerchant;
    }

    public void setBelongMerchant(int belongMerchant) {
        this.belongMerchant = belongMerchant;
    }
}
