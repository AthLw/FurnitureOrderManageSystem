package com.liuwei.interfaces;

import com.liuwei.entity.Commodity;
import com.liuwei.entity.Merchant;

import java.sql.SQLException;
import java.util.List;

public interface MerchantFunction {
    public Merchant login(int id);
    public void insert(Merchant merchant);
    public void update(Merchant merchant);
    public List<Commodity> browse(int merchantID);
    public void insert(Commodity commodity);
    public void update(Commodity commodity);
    public void close() throws SQLException;
    public void delete(int commodityID);
}
