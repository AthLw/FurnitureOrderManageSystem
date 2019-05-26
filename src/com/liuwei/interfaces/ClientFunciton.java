package com.liuwei.interfaces;

import com.liuwei.entity.Client;

import java.sql.SQLException;

public interface ClientFunciton {
    public Client login(int id);
    public void insert(Client client);
    public void update(Client client);
    public void close() throws SQLException;
}
