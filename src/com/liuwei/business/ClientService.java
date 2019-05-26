package com.liuwei.business;

import com.liuwei.entity.Client;
import com.liuwei.interfaces.ClientFunciton;
import com.liuwei.util.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName Client
 * @Description TODO
 * @Author AthLw
 * @Date 00:00 2019/5/25
 * @Version 1.0
 **/
public class ClientService implements ClientFunciton {
    private Connection conn;
    private PreparedStatement ps;

    public ClientService(){
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
        } catch (SQLException e) {
            System.out.println("query client failed!");
            e.printStackTrace();
        }

        return resClient;
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

}
