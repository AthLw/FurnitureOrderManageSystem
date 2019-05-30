package com.liuwei.business;

import com.liuwei.entity.Client;
import com.liuwei.entity.Commodity;
import com.liuwei.entity.Order;
import com.liuwei.interfaces.ClientFunction;
import com.liuwei.util.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Client
 * @Description TODO
 * @Author AthLw
 * @Date 00:00 2019/5/25
 * @Version 1.0
 **/
public class ClientService implements ClientFunction {
    private static Connection conn;
    private PreparedStatement ps;

    {
        conn = Connector.getInstance();
    }
    public Client login(int id){
        Client resClient = null;
        String selectStr = "select * from Client where 客户ID=?";
        try {
            ps = conn.prepareStatement(selectStr);
            ps.setInt(1, id);

            ResultSet res = ps.executeQuery();
            if(res.next()){
                resClient = new Client(id, res.getString("客户名"),
                        res.getDouble("消费总额"));
            }
            res.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("queryCommodity client failed!");
            e.printStackTrace();
        }

        return resClient;
    }

    public int insertByName(String clientName){
        String querySql = "select max(客户ID) from client";
        int cid = -1;
        try {
            ps = conn.prepareStatement(querySql);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                cid = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cid++;
        Client client = new Client(cid, clientName, 0);
        insert(client);
        return cid;
    }

    public void insert(Client client){
        String insertStr = "insert into Client values(?,?,?)";
        try {
            ps = conn.prepareStatement(insertStr);
            ps.setInt(1 ,client.getClientID());
            ps.setString(2, client.getClientName());
            ps.setDouble(3, client.getAllCost());

            int s = ps.executeUpdate();
            conn.commit();
            //System.out.println(s + " rows affected");
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Client client){
        String updateStr = "update client set 客户名=?,消费总额=? where 客户ID=?";
        try {
            ps = conn.prepareStatement(updateStr);
            ps.setString(1, client.getClientName());
            ps.setDouble(2, client.getAllCost());
            ps.setInt(3, client.getClientID());

            ps.executeUpdate();
            conn.commit();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() throws SQLException {
        conn.close();
    }

    public Commodity queryCertainCommodity(int commodityID){
        Commodity res = new Commodity();
        String querySql = "select * from commodity where 商品ID=?";
        try {
            ps = conn.prepareStatement(querySql);
            ps.setInt(1, commodityID);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                res.setCommodityID(commodityID);
                res.setCommodityName(resultSet.getString(2));
                res.setBelongToMerchant(resultSet.getInt(3));
                res.setDescription(resultSet.getString(4));
                res.setPrice(resultSet.getDouble(5));
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    @Override
    public List<Commodity> queryCommodity() {
        List<Commodity> list = new ArrayList<>();
        String querySql = "select * from commodity";
        try {
            ps = conn.prepareStatement(querySql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Commodity tmp = new Commodity();
                tmp.setCommodityID(resultSet.getInt(1));
                tmp.setCommodityName(resultSet.getString(2));
                tmp.setBelongToMerchant(resultSet.getInt(3));
                tmp.setDescription(resultSet.getString(4));
                tmp.setPrice(resultSet.getDouble(5));
                list.add(tmp);
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Order> queryOrder(int clientID) {
        List<Order> list = new ArrayList<>();
        String querySql = "select * from orderlist where 下单客户=?";
        try {
            ps = conn.prepareStatement(querySql);
            ps.setInt(1, clientID);
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
                System.out.println(tmp);
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
}
