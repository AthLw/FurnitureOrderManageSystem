package com.liuwei.interfaces;

import com.liuwei.entity.Commodity;
import com.liuwei.entity.CommodityInformation;
import com.liuwei.entity.Merchant;

import java.sql.SQLException;
import java.util.List;

public interface MerchantFunction {
    Merchant login(int id);
    void insert(Merchant merchant);
    void update(Merchant merchant);
    List<Commodity> queryCommodity(int merchantID);
    void insert(CommodityInformation commodity);
    void update(Commodity commodity);
    void close() throws SQLException;
    void delete(int commodityID);
}
