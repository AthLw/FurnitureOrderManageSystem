package com.liuwei.ui;

import com.liuwei.business.MerchantService;
import com.liuwei.entity.CommodityInformation;
import com.liuwei.entity.Merchant;
import com.liuwei.ui.dataLoader.MerchantComodDataLoader;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @ClassName MerchantUI
 * @Description TODO
 * @Author AthLw
 * @Date 20:00 2019/5/29
 * @Version 1.0
 **/
public class MerchantUI extends JFrame {
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel addCommodPane;
    private UserInfPane commodUIP;
    private JPanel showInformation;
    private MerchantComodDataLoader mcDataLoader;
    private TableScrollPane commodityPane;
    private UserInfPane userInfPane;
    private JTextField jLocation;
    private JTextField jName;
    private JTextField jCommodName;
    private JTextField jCommodDes;
    private JTextField jCommodPrice;

    private MerchantService merchantService;
    private int merchantID;
    private Merchant merchant;

    private int preRow;
    private String oldvalue;

    public void setCommodityPane(){
        String[][] tmp = new String[][]{};
        String[] names = new String[]{"商品ID", "商品名", "所属商户", "商品描述", "商品价格"};
        DefaultTableModel tableModel = new DefaultTableModel(tmp, names);
        JTable jTable = new JTable(tableModel);
        this.commodityPane = new TableScrollPane(jTable);
        mcDataLoader = new MerchantComodDataLoader(tableModel, this.merchantID);
        this.commodityPane.setDataLoader(mcDataLoader);
        tableModel.addTableModelListener(e -> {
            //TODO
            if(e.getType() == TableModelEvent.UPDATE){
                int i = e.getLastRow();
                int j = e.getColumn();
                String newvalue = jTable.getValueAt(i,j).toString();
                if(!newvalue.equals(oldvalue)){
                    mcDataLoader.setElement(i, j, newvalue);
                }
            }
        });
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                oldvalue = jTable.getValueAt(jTable.getSelectedRow(),
                        jTable.getSelectedColumn()).toString();
                System.out.println(oldvalue);
            }
        });
        this.commodityPane.setPreferredSize(new Dimension(500, 600));
        this.commodityPane.getViewport().setViewSize(new Dimension(500, 600));
    }

    public void setUserInfPane(){
        merchant = merchantService.login(this.merchantID);
        userInfPane.add("商户ID：", String.valueOf(merchant.getMerchantID()), false);
        jName = (JTextField) userInfPane.add("商户名：", merchant.getMerchantName(), true);
        jLocation = (JTextField) userInfPane.add("商户地址：", merchant.getMerchantLocation(), true);
        userInfPane.add("总收入：", String.valueOf(merchant.getAllSales()), false);
        userInfPane.addButton("修改", e -> {
           updateMerchant();
        });
        userInfPane.toPanel();
        userInfPane.setPreferredSize(new Dimension(400, 400));
    }

    public void setCommodUIP(){
        jCommodName = (JTextField) commodUIP.add("商品名：", "", true);
        jCommodName.setPreferredSize(new Dimension(200, 40));
        jCommodDes = (JTextField) commodUIP.add("商品描述：", "", true);
        jCommodDes.setPreferredSize(new Dimension(200, 80));
        jCommodPrice = (JTextField) commodUIP.add("商品价格：", "", true);
        jCommodPrice.setPreferredSize(new Dimension(200,40));
        commodUIP.addButton("添加商品", e -> {
            addCommodity();
        });
        commodUIP.toPanel();
        commodUIP.setPreferredSize(new Dimension(400, 400));

    }

    public void setPanel(){
        leftPanel.add(commodityPane);
        addCommodPane.add(commodUIP);
        //rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        //rightPanel.add(addCommodPane);
        showInformation.add(userInfPane);
        //rightPanel.add(showInformation);
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                true, addCommodPane, showInformation);
        jSplitPane.setDividerSize(20);
        rightPanel.add(jSplitPane);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
    }

    public void updateMerchant(){
        merchant.setMerchantName(jName.getText());
        merchant.setMerchantLocation(jLocation.getText());
        merchantService.update(merchant);
//        //userInfPane.setVisible(false);
//        rightPanel.remove(userInfPane);
//        userInfPane = new UserInfPane();
//        setUserInfPane();
//        rightPanel.add(userInfPane);
//        userInfPane.setVisible(true);

    }

    public void addCommodity(){
        String cName = jCommodName.getText();
        String cDes = jCommodDes.getText();
        double cPrice = Double.valueOf(jCommodPrice.getText());
        CommodityInformation commodityInformation = new
                CommodityInformation(cName, cDes, cPrice, this.merchantID);
        merchantService.insert(commodityInformation);
        jCommodPrice.setText("");
        jCommodDes.setText("");
        jCommodName.setText("");
        mcDataLoader.nextBatch();
    }

    public MerchantUI(int merchantID){
        this.preRow = -1;
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        userInfPane = new UserInfPane();
        commodUIP = new UserInfPane();
        addCommodPane = new JPanel();
        showInformation = new JPanel();
        this.merchantID = merchantID;
        merchantService = new MerchantService();

        setCommodityPane();
        setUserInfPane();
        setCommodUIP();
        setPanel();

        this.setBounds(0, 0, 1000, 900);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
