package com.liuwei.test;

import com.liuwei.business.DealService;
import com.liuwei.business.OrderService;
import com.liuwei.entity.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Test2
 * @Description TODO
 * @Author AthLw
 * @Date
 * @Version 1.0
 **/
public class Test2 {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        orderService.statisticCommodity();
        orderService.statisticMerchant();
    }
}
