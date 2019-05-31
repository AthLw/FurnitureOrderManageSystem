package com.liuwei.entity;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @ClassName Order
 * @Description save order in mysql database
 * @Author AthLw
 * @Date 2019/5/24 09:00
 * @Version 1.0
 **/
public class Order {
    private int orderID;
    private Timestamp orderTime;
    private int orderingClientID;
    private int commodityID;
    private int commodityNum;
    private double transactionCost;
    private boolean isRefund;

    public Order(int orderID, Timestamp orderTime, int orderingClientID, int commodityID,
                 int commodityNum, double transactionCost, boolean isRefund) {
        this.orderID = orderID;
        this.orderTime = orderTime;
        this.orderingClientID = orderingClientID;
        this.commodityID = commodityID;
        this.commodityNum = commodityNum;
        this.transactionCost = transactionCost;
        this.isRefund = isRefund;
    }

    public Order(){}

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public int getOrderingClientID() {
        return orderingClientID;
    }

    public void setOrderingClientID(int orderingClientID) {
        this.orderingClientID = orderingClientID;
    }

    public int getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(int commodityID) {
        this.commodityID = commodityID;
    }

    public int getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(int commodityNum) {
        this.commodityNum = commodityNum;
    }

    public double getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(double transactionCost) {
        this.transactionCost = transactionCost;
    }

    public boolean isRefund() {
        return isRefund;
    }

    public void setRefund(boolean refund) {
        isRefund = refund;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", orderTime='" + orderTime + '\'' +
                ", orderingClientID=" + orderingClientID +
                ", commodityID=" + commodityID +
                ", commodityNum=" + commodityNum +
                ", transactionCost=" + transactionCost +
                ", isRefund=" + isRefund +
                '}';
    }

    public String[] toStringArray(){
        String[] res = new String[7];
        res[0] = String.valueOf(orderID);
        res[1] = String.valueOf(orderTime);
        res[2] = String.valueOf(orderingClientID);
        res[3] = String.valueOf(transactionCost);
        res[4] = String.valueOf(commodityID);
        res[5] = String.valueOf(commodityNum);
        res[6] = String.valueOf(isRefund);
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID &&
                orderingClientID == order.orderingClientID &&
                commodityID == order.commodityID &&
                commodityNum == order.commodityNum &&
                Double.compare(order.transactionCost, transactionCost) == 0 &&
                isRefund == order.isRefund &&
                Objects.equals(orderTime, order.orderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, orderTime, orderingClientID, commodityID, commodityNum, transactionCost, isRefund);
    }
}
