package com.liuwei.business;

import com.liuwei.entity.Commodity;
import com.liuwei.entity.Merchant;
import com.liuwei.interfaces.MerchantFunction;
import com.liuwei.util.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Merchant
 * @Description TODO
 * @Author AthLw
 * @Date 00:00 2019/5/25
 * @Version 1.0
 **/
public class MerchantService implements MerchantFunction {
    private Connection conn;
    private PreparedStatement ps;

    public MerchantService(){
        conn = Connector.getInstance();
    }

    public Merchant login(int id){
        Merchant resMerchant = null;
        String selectStr = "select * from merchant where 商户ID=?";
        try {
            ps = conn.prepareStatement(selectStr);
            ps.setInt(1, id);

            ResultSet res = ps.executeQuery();
            if(res.next()){
                resMerchant = new Merchant(id, res.getString("商户名"),
                        res.getString("商户地址"), res.getDouble("销售总额"));
            }
            res.close();
        } catch (SQLException e) {
            System.out.println("query merchant failed!");
            e.printStackTrace();
        }
        return resMerchant;
    }

    public void insert(Merchant merchant){
        String insertStr = "insert into Merchant values(?,?,?,?)";
        try {
            ps = conn.prepareStatement(insertStr);
            ps.setInt(1, merchant.getMerchantID());
            ps.setString(2, merchant.getMerchantName());
            ps.setString(3, merchant.getMerchantLocation());
            ps.setDouble(4, merchant.getAllSales());

            conn.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println("insert merchant failed");
            e.printStackTrace();
        }
    }

    public void update(Merchant merchant){
        String updateStr = "update merchant set 商户名=?,商户地址=?,销售总额=? where 商户ID=?";
        try {
            ps = conn.prepareStatement(updateStr);
            ps.setString(1, merchant.getMerchantName());
            ps.setString(2, merchant.getMerchantLocation());
            ps.setDouble(3, merchant.getAllSales());
            ps.setInt(4, merchant.getMerchantID());

            ps.executeUpdate();
            conn.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println("update merchant failed");
            e.printStackTrace();
        }
    }

    @Override
    public List<Commodity> browse(int merchantID) {
        List<Commodity> list = new ArrayList<>();
        String selectStr = "select * from commodity where 所属商户=?";
        try {
            ps = conn.prepareStatement(selectStr);
            ps.setInt(1, merchantID);

            ResultSet res = ps.executeQuery();
            while(res.next()){
                Commodity tmp = new Commodity();
                tmp.setCommodityID(res.getInt("商品ID"));
                tmp.setCommodityName(res.getString("商品名"));
                tmp.setBelongToMerchant(res.getInt("所属商户"));
                tmp.setDescription(res.getString("商品描述"));
                tmp.setPrice(res.getDouble("商品价格"));

                list.add(tmp);
            }
            res.close();
        } catch (SQLException e) {
            System.out.println("query commodity failed");
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insert(Commodity commodity) {
        String insertStr = "insert into commodity values(?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(insertStr);
            ps.setInt(1, commodity.getCommodityID());
            ps.setString(2, commodity.getCommodityName());
            ps.setInt(3, commodity.getBelongToMerchant());
            ps.setString(4, commodity.getDescription());
            ps.setDouble(5, commodity.getPrice());

            ps.executeUpdate();
            conn.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println("insert commodity failed");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Commodity commodity) {
        String updateStr = "update commodity set 商品名=?,商品描述=?,商品价格=? where 商品ID=?";
        try {
            ps = conn.prepareStatement(updateStr);
            ps.setString(1, commodity.getCommodityName());
            ps.setString(2, commodity.getDescription());
            ps.setDouble(3, commodity.getPrice());
            ps.setInt(4, commodity.getCommodityID());

            ps.executeUpdate();
            conn.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println("update commodity failed");
            e.printStackTrace();
        }
    }

    public void delete(int commodityID){
        String delStr = "delete from commodity where 商品ID=?";
        try {
            ps = conn.prepareStatement(delStr);
            ps.setInt(1, commodityID);

            ps.executeUpdate();
            conn.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println("delete commodity failed!");
            e.printStackTrace();
        }
    }

    public void close() throws SQLException {
        conn.close();
    }
}
