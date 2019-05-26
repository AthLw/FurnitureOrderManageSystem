package com.liuwei.interfaces;

import com.liuwei.entity.Order;

import java.util.List;

public interface OrderFunction {
    List<Order> query(int startLoc);
    boolean checkMerchant();
    boolean checkClient();
    int statisticMerchant();
    int statisticCommodity();
}
