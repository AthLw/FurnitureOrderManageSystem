package com.liuwei.interfaces;

import com.liuwei.entity.Order;
import com.liuwei.entity.OrderInformation;

import java.util.List;

public interface DealFunction {
    double bargain(int clientID, List<OrderInformation> commoditylist);
    double refund(int orderID);
}
