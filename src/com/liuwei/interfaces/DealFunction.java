package com.liuwei.interfaces;

import com.liuwei.entity.Order;

import java.util.List;

public interface DealFunction {
    void bargain(int clientID, List<int[]> commoditylist);
    boolean refund(int orderID);
}
