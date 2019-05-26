package com.liuwei.entity;

/**
 * @ClassName Client
 * @Description save Client in mysql database
 * @Author AthLw
 * @Date 2019/5/24 09:00
 * @Version 1.0
 **/
public class Client {
    private int clientID;
    private String clientName;
    private double allCost;

    public Client(int clientID, String clientName, double allCost) {
        this.clientID = clientID;
        this.clientName = clientName;
        this.allCost = allCost;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void setAllCost(double allCost) {
        this.allCost = allCost;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public double getAllCost() {
        return allCost;
    }

    public void addCost(double cost){
        this.allCost += cost;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientID=" + clientID +
                ", clientName='" + clientName + '\'' +
                ", allCost=" + allCost +
                '}';
    }
}
