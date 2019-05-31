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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @ClassName MerchantUI
 * @Description 商户界面
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

    private String oldValue;

    private void setCommodityPane(){
        String[][] tmp = new String[][]{};
        String[] names = new String[]{"商品ID", "商品名", "所属商户", "商品描述", "商品价格"};
        DefaultTableModel tableModel = new DefaultTableModel(tmp, names);
        JTable jTable = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 0 || column==2){
                    return false;
                }else{
                    return true;
                }
            }
        };
        this.commodityPane = new TableScrollPane(jTable);
        mcDataLoader = new MerchantComodDataLoader(tableModel, this.merchantID);
        this.commodityPane.setDataLoader(mcDataLoader);
        tableModel.addTableModelListener(e -> {
            if(e.getType() == TableModelEvent.UPDATE){
                int i = e.getLastRow();
                int j = e.getColumn();
                String newValue = jTable.getValueAt(i,j).toString();
                if(!newValue.equals(oldValue)){
                    mcDataLoader.setElement(i, j, newValue);
                }
            }
        });
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(jTable.getSelectedColumn() != 0 && jTable.getSelectedColumn() != 2)
                     oldValue = jTable.getValueAt(jTable.getSelectedRow(),
                            jTable.getSelectedColumn()).toString();
            }
        });
        this.commodityPane.setPreferredSize(new Dimension(500, 450));
        this.commodityPane.getViewport().setViewSize(new Dimension(500, 450));
    }

    public void setUserInfPane(){
        queryMerchant();
        userInfPane.add("商户ID：", String.valueOf(merchant.getMerchantID()), false);
        jName = (JTextField) userInfPane.add("商户名：", merchant.getMerchantName(), true);
        jLocation = (JTextField) userInfPane.add("商户地址：", merchant.getMerchantLocation(), true);
        userInfPane.add("总收入：", String.valueOf(merchant.getAllSales()), false);
        userInfPane.addButton("修改", e -> {
           updateMerchantPane();
        });
        userInfPane.toPanel();
        userInfPane.setPreferredSize(new Dimension(400, 250));
    }

    public void setCommodUIP(){
        jCommodName = (JTextField) commodUIP.add("商品名：", "", true);
        jCommodName.setPreferredSize(new Dimension(200, 30));
        jCommodDes = (JTextField) commodUIP.add("商品描述：", "", true);
        jCommodDes.setPreferredSize(new Dimension(200, 50));
        jCommodPrice = (JTextField) commodUIP.add("商品价格：", "", true);
        jCommodPrice.setPreferredSize(new Dimension(200,30));
        commodUIP.addButton("添加商品", e -> {
            addCommodity();
        });
        commodUIP.toPanel();
        commodUIP.setPreferredSize(new Dimension(400, 200));

    }

    public void setPanel(){
        leftPanel.add(commodityPane);
        addCommodPane.add(commodUIP);
        addCommodPane.setPreferredSize(new Dimension(400, 200));
        showInformation.add(userInfPane);
        showInformation.setPreferredSize(new Dimension(400, 250));
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                true, addCommodPane, showInformation);
        jSplitPane.setDividerSize(5);
        rightPanel.add(jSplitPane);
        rightPanel.setPreferredSize(new Dimension(400, 450));

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
    }

    private void updateMerchant(){
        queryMerchant();
        merchant.setMerchantName(jName.getText());
        merchant.setMerchantLocation(jLocation.getText());
        merchantService.update(merchant);
    }

    private void queryMerchant(){
        merchant = merchantService.login(this.merchantID);
    }

    private void updateMerchantPane(){
        updateMerchant();
        userInfPane.setVisible(false);
        showInformation.remove(userInfPane);
        userInfPane = new UserInfPane();
        setUserInfPane();
        showInformation.add(userInfPane);
        userInfPane.setVisible(true);
    }

    private void addCommodity(){
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

        this.setBounds(0, 0, 1000, 800);
        this.pack();
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new mainWindow();
            }
        });
    }
}
