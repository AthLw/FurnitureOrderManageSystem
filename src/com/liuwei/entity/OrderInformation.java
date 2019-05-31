package com.liuwei.entity;

/**
 * @ClassName OrderInformation
 * @Description 记录下单时需要的信息：商品id，商品数量，花费
 * @Author AthLw
 * @Date 14:00 2019/5/28
 * @Version 1.0
 **/
public class OrderInformation {
        private int commodityNum;
        private int commodityID;
        private double transCost;

        public OrderInformation(int commodityID, int commodityNum, double price) {
            this.commodityNum = commodityNum;
            this.commodityID = commodityID;
            this.transCost = price;
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

        public double getTransCost() {
            return transCost;
        }

        public void setTransCost(double transCost) {
            this.transCost = transCost;
        }

    @Override
    public String toString() {
        return "OrderInformation{" +
                "commodityNum=" + commodityNum +
                ", commodityID=" + commodityID +
                ", transCost=" + transCost +
                '}';
    }
}
