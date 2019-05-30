package com.liuwei.ui;

import com.liuwei.business.ClientService;
import com.liuwei.business.DealService;
import com.liuwei.business.OrderService;
import com.liuwei.entity.Client;
import com.liuwei.entity.OrderInformation;
import com.liuwei.ui.dataLoader.ClientCommodityDataLoader;
import com.liuwei.ui.dataLoader.ClientOrderDataLoader;
import com.liuwei.ui.dataLoader.OrderDataLoader;
import com.mysql.cj.conf.BooleanPropertyDefinition;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ClientUI
 * @Description TODO
 * @Author AthLw
 * @Date 19:00 2019/5/28
 * @Version 1.0
 **/
public class ClientUI extends JFrame {
    private JPanel[][] jPanels;
    private GridLayout gridLayout;
    private TableScrollPane commodityPane;
    private ClientCommodityDataLoader ccDataLoader;
    private TableScrollPane orderPane;
    private ClientOrderDataLoader coDataLoader;
    private JTextField inputList;
    private JButton order;
    private JButton refund;
    private JTextField jname;

    private ClientService clientService;
    private DealService dealService;

    private UserInfPane userInfPane;
    private int clientID;
    private Client client;

    public void setUserInfPane(){
        client = clientService.login(this.clientID);
        userInfPane.add("用户ID：", String.valueOf(client.getClientID()), false);
        jname = (JTextField) userInfPane.add("用户名：", client.getClientName(), true);
        userInfPane.add("总消费金额：", client.getAllCost()+"", false);
        userInfPane.addButton("修改", e -> {
            updateClient();
        });
        userInfPane.toPanel();
        userInfPane.setPreferredSize(new Dimension(200, 200));
    }

    public void setCommodityPane(){
        String[][] tmp = new String[][]{};
        String[] names = new String[]{"商品号","商品名","所属商户","商品描述","商品价格"};
        DefaultTableModel tableModel = new DefaultTableModel(tmp, names);
        JTable jTable = new JTable(tableModel);
        this.commodityPane = new TableScrollPane(jTable);
        ccDataLoader = new ClientCommodityDataLoader(tableModel);
        this.commodityPane.setDataLoader(ccDataLoader);

        this.commodityPane.setPreferredSize(new Dimension(400, 200));
        this.commodityPane.getViewport().setViewSize(new Dimension(400, 200));
    }

    public void setOrderPane(){
        String[][] tmp = new String[][]{};
        String[] names = new String[]{"订单号","下单时间","下单客户","交易金额","商品ID","商品数量", "是否退货"};
        DefaultTableModel tableModel = new DefaultTableModel(tmp, names);
        JTable jTable = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        this.orderPane = new TableScrollPane(jTable);
        coDataLoader = new ClientOrderDataLoader(tableModel, this.clientID);
        this.orderPane.setDataLoader(coDataLoader);

        orderPane.setPreferredSize(new Dimension(400, 200));
        orderPane.getViewport().setViewSize(new Dimension(400, 200));
    }

    public void setPanel(){
        this.add(jPanels[0][0]);
        this.add(jPanels[0][1]);
        this.add(jPanels[1][0]);
        this.add(jPanels[1][1]);
        jPanels[0][0].add(commodityPane);
        jPanels[1][0].add(orderPane);

        JPanel rightButtonPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2,0));
        rightPanel.add(inputList);
        rightButtonPanel.add(order);
        rightButtonPanel.add(refund);
        rightPanel.add(rightButtonPanel);
        jPanels[0][1].add(rightPanel);

        jPanels[1][1].add(userInfPane);

//        mainPanel.add(leftPanel, BorderLayout.WEST);
//        mainPanel.add(rightPanel, BorderLayout.EAST);
    }

    public void updateClient(){
        client.setClientName(jname.getText());
        clientService.update(client);
        userInfPane.setVisible(false);
        this.remove(userInfPane);
        userInfPane = new UserInfPane();
        setUserInfPane();
        this.add(userInfPane);
        userInfPane.setVisible(true);
    }

    public void updateOrder(){
        List<OrderInformation> list = this.toList();
        dealService.bargain(this.clientID, list);
        coDataLoader.nextBatch();
        orderPane.setVisible(true);
    }

    public void refund(){
        int orderid = this.getOrderID();
        dealService.refund(orderid);
        coDataLoader.nextBatch();

    }

    public ClientUI(int clientID){
        this.clientID = clientID;
        gridLayout = new GridLayout(2, 2, 10,10);
        this.setLayout(gridLayout);
        jPanels = new JPanel[2][2];
        for(int i =0 ; i< 2; i++){
            for(int j = 0; j <2; j++){
                jPanels[i][j] = new JPanel();
            }
        }
        userInfPane = new UserInfPane();
        clientService = new ClientService();
        dealService = new DealService();
        order = new JButton("下单");
        order.addActionListener(e -> {
            updateOrder();
        });
        refund = new JButton("退货");
        refund.addActionListener(e -> {
            refund();
        });
        inputList = new JTextField(10);
        inputList.setPreferredSize(new Dimension(300, 100));

        setCommodityPane();
        setOrderPane();
        setUserInfPane();
        setPanel();
//        JPanel mainpanel = setPanel();
//        this.add(mainpanel, BorderLayout.CENTER);
        this.setBounds(0, 0, 1600, 1200);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public int getOrderID(){
        String s = inputList.getText();
        return Integer.valueOf(s);
    }

    private List<OrderInformation> toList(){
        List<OrderInformation> list = new ArrayList<>();
        String s = inputList.getText();
        String[] array = s.split(",|，| |\n");
        for(String tmp : array){
            String[] item = tmp.split("-");
            int commodityID = Integer.valueOf(item[0]);
            int commodityNum = Integer.valueOf(item[1]);
            System.out.println("cid:  "+commodityID+ "   cNum: "+commodityNum);
            double commodityPrice = clientService.queryCertainCommodity(commodityID).getPrice();
            OrderInformation orderInformation = new OrderInformation(commodityID, commodityNum, commodityPrice);
            list.add(orderInformation);
            System.out.println(orderInformation);
        }
        return list;
    }
}
