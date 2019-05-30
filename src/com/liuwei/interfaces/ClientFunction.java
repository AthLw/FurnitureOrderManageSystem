package com.liuwei.interfaces;

import com.liuwei.entity.Client;
import com.liuwei.entity.Commodity;
import com.liuwei.entity.Order;

import java.sql.SQLException;
import java.util.List;

public interface ClientFunction {
    Client login(int id);
    List<Commodity> queryCommodity();
    List<Order> queryOrder(int clientID);
    void insert(Client client);
    void update(Client client);
    void close() throws SQLException;
}
