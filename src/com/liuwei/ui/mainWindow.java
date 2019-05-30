package com.liuwei.ui;

import com.liuwei.business.ClientService;
import com.liuwei.business.MerchantService;
import com.liuwei.business.OrderService;
import com.liuwei.entity.Order;
import com.liuwei.ui.dataLoader.OrderDataLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.PipedReader;
import java.util.List;

/**
 * @ClassName mainWindow
 * @Description TODO
 * @Author AthLw
 * @Date 09:00 2019/5/28
 * @Version 1.0
 **/
public class mainWindow extends JFrame{
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private TableScrollPane tableScrollPane;
    private UserInfPane loginUIP;
    private JTextField ID;
    private JButton login;
    private JButton register;
    private JRadioButton[] identity;
    private ButtonGroup buttonGroup;
    private OrderDataLoader orderDataLoader;
    private List<Order> orderList;
    private OrderService orderService;
    private ClientService clientService;
    private MerchantService merchantService;
    private RegisterDialog registerDialog;

    public void setTableScrollPane() {
        String[][] tmp = new String[][]{};
        String[] names = new String[]{"订单号","下单时间","下单客户","交易金额","是否退货","商品ID","商品数量"};
        DefaultTableModel tableModel = new DefaultTableModel(tmp, names);
        JTable jTable = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        this.tableScrollPane = new TableScrollPane(jTable);
        this.tableScrollPane.setDataLoader(new OrderDataLoader(tableModel));

        tableScrollPane.setSize(950, 500);
        tableScrollPane.getViewport().setViewSize(new Dimension(800, 500));
    }

    public void setLoginUIP(){
         ID = (JTextField) loginUIP.add("ID: ", "", true);
         ID.setPreferredSize(new Dimension(70, 20));
         loginUIP.addParallelButton("登录", "注册",
                 e->{
                    login();
             },
                 e->{
                    register();
         });
         loginUIP.toPanel();
    }

    public void login(){
        if(this.identity[0].isSelected()){
            int cid = Integer.valueOf(this.ID.getText());
            this.setVisible(false);
            new ClientUI(cid);
        }else{
            int mid = Integer.valueOf(this.ID.getText());
            this.setVisible(false);
            new MerchantUI(mid);
        }
    }

    public void register(){
        if(this.identity[0].isSelected()){
            registerDialog = new RegisterDialog(this, true);
            JTextField textField = (JTextField) registerDialog.add("客户名", "");
            textField.setPreferredSize(new Dimension(100, 25));
            registerDialog.showDialog();

        }else{
            registerDialog = new RegisterDialog(this, true);
            JTextField textField = (JTextField) registerDialog.add("商户名", "");
            JTextField textField1 = (JTextField) registerDialog.add("商户地址", "");
            textField.setPreferredSize(new Dimension(100, 25));
            textField1.setPreferredSize(new Dimension(100, 50));
            registerDialog.showDialog();
        }
    }

    public void closeDialog(){
        registerDialog.dispose();
    }
    public void setPanel(){
        rightPanel.setSize(1200, 1000);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(loginUIP);
        JPanel jPanel = new JPanel();
        for(int i = 0; i < identity.length; i++){
            jPanel.add(identity[i]);
        }
        leftPanel.add(jPanel);
        leftPanel.setSize(400,400);
        rightPanel.add(tableScrollPane);
        this.mainPanel.add(leftPanel, BorderLayout.WEST);
        this.mainPanel.add(rightPanel, BorderLayout.CENTER);
        this.add(this.mainPanel, BorderLayout.CENTER);
    }

    public void setRadioButton(){
        JRadioButton clientRB = new JRadioButton("客户", true);
        JRadioButton merchantRB = new JRadioButton("商户");
        this.identity = new JRadioButton[]{
                clientRB, merchantRB
        };
        buttonGroup = new ButtonGroup();
        buttonGroup.add(clientRB);
        buttonGroup.add(merchantRB);
    }

    public mainWindow(){
        changeStyle();
        this.mainPanel = new JPanel();
        this.leftPanel = new JPanel();
        this.rightPanel = new JPanel();
        this.loginUIP = new UserInfPane();
        this.clientService = new ClientService();
        this.merchantService = new MerchantService();

        setRadioButton();
        setLoginUIP();
        setTableScrollPane();
        setPanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0,0,2000, 1500);
        this.setTitle("家具订单管理");
        this.pack();
        this.setVisible(true);
    }

    public void changeStyle(){
        String lookAndFeel = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
        //lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new mainWindow();
    }
}
