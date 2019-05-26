package com.liuwei.test;

import com.liuwei.business.ClientService;
import com.liuwei.entity.Client;

import java.sql.SQLException;

/**
 * @ClassName Test
 * @Description TODO
 * @Author AthLw
 * @Date 13:00 2019/5/25
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
        ClientService clientService  = new ClientService();
        Client client = new Client(2, "李梦娇", 90.9);
        clientService.update(client);
        System.out.println(client);
        try {
            clientService.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
