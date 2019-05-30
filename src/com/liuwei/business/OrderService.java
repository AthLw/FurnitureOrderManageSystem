package com.liuwei.business;

import com.liuwei.entity.Order;
import com.liuwei.interfaces.OrderFunction;
import com.liuwei.util.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OrderService
 * @Description TODO
 * @Author AthLw
 * @Date 18:00 2019/5/26
 * @Version 1.0
 **/
public class OrderService implements OrderFunction {
    public static int ITEM_NUM_PER_PAGE = 20;

    private static Connection conn;
    private PreparedStatement ps;

    public OrderService(){
        conn = Connector.getInstance();
    }
    @Override
    public List<Order> query(int startLoc) {
        List<Order> list = new ArrayList<Order>(ITEM_NUM_PER_PAGE);
        String querySql = "select * from orderlist limit ?,"+ITEM_NUM_PER_PAGE;
        try {
            ps = conn.prepareStatement(querySql);
            ps.setInt(1, startLoc);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                Order tmp = new Order();
                tmp.setOrderID(resultSet.getInt(1));
                tmp.setOrderTime(resultSet.getTimestamp(2));
                tmp.setOrderingClientID(resultSet.getInt(3));
                tmp.setTransactionCost(resultSet.getDouble(4));
                tmp.setRefund(resultSet.getBoolean(5));
                tmp.setCommodityID(resultSet.getInt(6));
                tmp.setCommodityNum(resultSet.getInt(7));

                list.add(tmp);
            }
            resultSet.close();
            conn.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public boolean checkClient(){
        boolean flag = true;
        String checkSql = "select 下单客户,消费总额,sum(交易金额) from orderlist,client" +
                "where 下单客户=客户ID group by 下单客户";
        try {
            ps = conn.prepareStatement(checkSql);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                int clientid = res.getInt(1);
                double cost = res.getDouble(2);
                double transSum = res.getDouble(3);
                if(Math.abs(cost-transSum) > 0.01){
                    flag = false;
                    System.out.println("客户出现不一致！\n"+
                            "client: " + clientid + "\n" +
                            "cost: "+ cost + "   transSum: "+ transSum);
                }
            }
            res.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean checkMerchant(){
        boolean flag = true;
        String checkSql = "select 商户ID,销售总额,sum(交易金额) from " +
                "(commodity inner join merchant on commodity.所属商户=merchant.商户ID)" +
                "inner join orderlist on commodity.商品ID=orderlist.商品ID" +
                "group by 商户ID";
        try {
            ps = conn.prepareStatement(checkSql);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                int merchantid = res.getInt(1);
                double sales = res.getDouble(2);
                double transSum = res.getDouble(3);
                if(Math.abs(sales-transSum) > 0.01){
                    flag = false;
                    System.out.println("商户出现不一致！\n"+
                            "merchant: " + merchantid + "\n" +
                            "cost: "+ sales + "   transSum: "+ transSum);
                }
            }
            res.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public int statisticMerchant() {
        int res = 0;
        String querySql = "select a.所属商户,max(a.s) from" +
                "(select 所属商户,sum(交易金额) as s from orderlist inner join commodity on orderlist.商品ID = commodity.商品ID " +
                "where 下单时间 > DATE_SUB(CURDATE(),INTERVAL 1 MONTH)" +
                "group by 所属商户) as a";
        try {
            ps = conn.prepareStatement(querySql);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                int merchantid = resultSet.getInt(1);
                double income = resultSet.getDouble(2);
                res = merchantid;
                System.out.println("the merchant which has highest income is "+merchantid
                + "\n income:  "+ income);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int statisticCommodity() {
        //TODO
        int res = 0;
        String querySql = "select a.商品ID,max(a.s) from " +
                "(select 商品ID,sum(交易金额) as s from orderlist " +
                "where 下单时间 > DATE_SUB(curdate(),interval 1 month) " +
                "group by 商品ID) as a";
        try {
            ps = conn.prepareStatement(querySql);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                int commodityid = resultSet.getInt(1);
                double allmoney = resultSet.getDouble(2);
                res = commodityid;
                System.out.println("the commodity which has highest income is "+commodityid
                        + "\n income:  "+ allmoney);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
