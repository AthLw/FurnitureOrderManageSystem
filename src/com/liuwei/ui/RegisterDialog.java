package com.liuwei.ui;

import com.liuwei.business.ClientService;
import com.liuwei.business.MerchantService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RegisterDialog
 * @Description 注册对话框，用于注册商户或客户
 * @Author AthLw
 * @Date 12:00 2019/5/30
 * @Version 1.0
 **/
public class RegisterDialog extends JDialog {
    private UserInfPane userInfPane;
    private List<JComponent> componentList;
    private JButton register;
    private ClientService clientService;
    private MerchantService merchantService;

    public JComponent add(String name, String value){
        JComponent component = userInfPane.add(name, value, true);
        componentList.add(component);
        return component;
    }

    public RegisterDialog(Frame owner, boolean modal) {
        super(owner, modal);
        this.userInfPane = new UserInfPane();
        componentList = new ArrayList<>();
        clientService = new ClientService();
        merchantService = new MerchantService();
    }

    public void showDialog(){
        register = userInfPane.addButton("注册", null);
        userInfPane.toPanel();
        register.addActionListener(e -> {
            int num = componentList.size();
            int id;
            if(num == 1){
                String cname = ((JTextField)componentList.get(0)).getText();
                id = clientService.insertByName(cname);
            }else{
                String mname = null;
                String mloc = null;
                for(int i = 0; i < 2; i++){
                    mname = ((JTextField)componentList.get(0)).getText();
                    mloc = ((JTextField)componentList.get(1)).getText();
                }
                id = merchantService.insertByName(mname, mloc);
            }
            JOptionPane.showConfirmDialog(null, "您获得的ID是："+id,
                    "回应", JOptionPane.YES_OPTION);
            this.dispose();
        });
        this.add(userInfPane);
        this.setBounds(100, 100, 500, 300);
        this.setVisible(true);
    }
}
