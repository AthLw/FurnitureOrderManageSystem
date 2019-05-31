package com.liuwei.business;

import com.liuwei.entity.OrderInformation;
import com.liuwei.interfaces.DealFunction;
import com.liuwei.util.Connector;

import java.sql.*;
import java.util.List;

/**
 * @ClassName DealService
 * @Description 交易类，负责下单和退货
 * @Author AthLw
 * @Date 10:00 2019/5/26
 * @Version 1.0
 **/
public class DealService implements DealFunction {
    private static Connection conn;
    private PreparedStatement ps;

    {
        conn = Connector.getInstance();
    }

    @Override
    public double bargain(int clientID, List<OrderInformation> commoditylist) {
        String querySql = "select max(订单号) from orderlist";
        String insertSql = "insert into orderlist values(?, ?, ?, ?, ?, ?, ?)";
        String queryMerchantsql = "select 所属商户 from commodity where 商品ID=?";
        String merchantOrderSql = "update merchant set 销售总额=销售总额+? where 商户ID=?";
        String clientOrderSql = "update client set 消费总额=消费总额+? where 客户ID=?";
        int orderID = 0;
        double orderMoney = 0;
        int mid = -1;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(querySql);
            ResultSet res =  ps.executeQuery();
            if(res.next()){
                orderID = res.getInt(1);
                System.out.println(orderID);
            }
            res.close();

            orderID++;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            for(OrderInformation tmp: commoditylist){
                ps = conn.prepareStatement(insertSql);
                ps.setInt(1, orderID);
                ps.setTimestamp(2, timestamp);
                ps.setInt(3, clientID);
                ps.setDouble(4, tmp.getTransCost());
                orderMoney += tmp.getTransCost();
                ps.setInt(5, 0);
                ps.setInt(6, tmp.getCommodityID());
                ps.setInt(7, tmp.getCommodityNum());
                ps.executeUpdate();

                ps = conn.prepareStatement(queryMerchantsql);
                ps.setInt(1, tmp.getCommodityID());
                ResultSet resultSet = ps.executeQuery();
                if(resultSet.next()){
                    mid = resultSet.getInt(1);
                }
                resultSet.close();

                ps = conn.prepareStatement(merchantOrderSql);
                ps.setDouble(1, tmp.getTransCost());
                ps.setInt(2, mid);
                ps.executeUpdate();
            }

            ps = conn.prepareStatement(clientOrderSql);
            ps.setDouble(1, orderMoney);
            System.out.println("money: "+orderMoney+ "  "+ "client: "+clientID);
            ps.setInt(2, clientID);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.out.println("bargain failed!");
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orderMoney;
    }

    @Override
    public double refund(int orderID) {
        String qeurySql = "select 所属商户,下单客户,交易金额 " +
                "from orderlist inner join commodity on orderlist.商品ID=commodity.商品ID where 订单号=?";
        String updateSql = "update orderlist set 是否退货=? where 订单号=?";
        String merchantRefundSql = "update merchant set 销售总额=销售总额-? where 商户ID=?";
        String clientRefundSql = "update client set 消费总额=消费总额-? where 客户ID=?";
        double ordermoney = 0;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(updateSql);
            ps.setInt(1, 1);
            ps.setInt(2, orderID);
            ps.executeUpdate();

            ps = conn.prepareStatement(qeurySql);
            ps.setInt(1, orderID);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                int merchantid = resultSet.getInt(1);
                int clientid = resultSet.getInt(2);
                double money = resultSet.getDouble(3);
                ordermoney += money;

                ps = conn.prepareStatement(merchantRefundSql);
                ps.setDouble(1, money);
                ps.setInt(2, merchantid);
                ps.executeUpdate();

                ps = conn.prepareStatement(clientRefundSql);
                ps.setDouble(1, money);
                ps.setInt(2, clientid);
                ps.executeUpdate();
            }
            resultSet.close();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ordermoney;
    }
}
