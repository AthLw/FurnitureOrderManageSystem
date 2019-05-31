package com.liuwei.entity;

/**
 * @ClassName Commodity
 * @Description save commodity in mysql database
 * @Author AthLw
 * @Date 2019/5/24 09:00
 * @Version 1.0
 **/
public class Commodity {
    private int commodityID;
    private String commodityName;
    private int belongToMerchant;
    private String description;
    private double price;

    public Commodity(int commodityID, String commodityName, int belongToMerchant, String description, double price) {
        this.commodityID = commodityID;
        this.commodityName = commodityName;
        this.belongToMerchant = belongToMerchant;
        this.description = description;
        this.price = price;
    }
    public Commodity(){}

    public int getCommodityID() {
        return commodityID;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public int getBelongToMerchant() {
        return belongToMerchant;
    }

    public void setCommodityID(int commodityID) {
        this.commodityID = commodityID;
    }

    public void setBelongToMerchant(int belongToMerchant) {
        this.belongToMerchant = belongToMerchant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "commodityID=" + commodityID +
                ", commodityName='" + commodityName + '\'' +
                ", belongToMerchant=" + belongToMerchant +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    public String[] toStringArray(){
        String[] res = new String[5];
        res[0] = String.valueOf(commodityID);
        res[1] = String.valueOf(commodityName);
        res[2] = String.valueOf(belongToMerchant);
        res[3] = String.valueOf(description);
        res[4] = String.valueOf(price);
        return res;
    }

    public void setAttrib(int i, String value){
        switch (i){
            case 1:
                this.setCommodityName(value);
                break;
            case 3:
                this.setDescription(value);
                break;
            case 4:
                this.setPrice(Double.valueOf(value));
                break;
            default:
                System.out.println("Error! try to modify read-only data");
                break;

        }
    }
}
