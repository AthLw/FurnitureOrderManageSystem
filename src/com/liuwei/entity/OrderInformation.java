package com.liuwei.entity;

/**
 * @ClassName OrderInformation
 * @Description TODO
 * @Author AthLw
 * @Date 14:00 2019/5/28
 * @Version 1.0
 **/
public class OrderInformation {
        private int commodityNum;
        private int commodityID;
        private double price;

        public OrderInformation(int commodityID, int commodityNum, double price) {
            this.commodityNum = commodityNum;
            this.commodityID = commodityID;
            this.price = price;
        }

        public int getCommodityNum() {
            return commodityNum;
        }

        public void setCommodityNum(int commodityNum) {
            this.commodityNum = commodityNum;
        }

        public int getCommodityID() {
            return commodityID;
        }

        public void setCommodityID(int commodityID) {
            this.commodityID = commodityID;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

}
