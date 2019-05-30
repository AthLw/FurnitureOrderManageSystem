package com.liuwei.ui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserInfPane
 * @Description TODO
 * @Author AthLw
 * @Date 13:00 2019/5/29
 * @Version 1.0
 **/
public class UserInfPane extends JPanel {
    private List<JPanel> information;

    public UserInfPane(){
        information = new ArrayList<>();
    }

    public JComponent add(String name, String value, boolean canChanged){
        JLabel tmpName = new JLabel(name);
        JPanel tmpInf = new JPanel();
        JComponent res = null;
        tmpInf.add(tmpName);
        if(canChanged){
            JTextField tmpfield = new JTextField(value);
            res = tmpfield;
            tmpInf.add(tmpfield);
        }else{
            JLabel tmplabel =  new JLabel(value);
            res = tmplabel;
            tmpInf.add(tmplabel);
        }
        information.add(tmpInf);
        return res;
    }

    public JButton addButton(String name, ActionListener actionListener){
        JButton jButton = new JButton(name);
        jButton.addActionListener(actionListener);
        JPanel tmp = new JPanel();
        tmp.add(jButton);
        information.add(tmp);
        return jButton;
    }

    public void addParallelButton(String leftName, String rightName,
                                  ActionListener actionListener1, ActionListener actionListener2){
        JButton jButton1 = new JButton(leftName);
        jButton1.addActionListener(actionListener1);
        JButton jButton2 = new JButton(rightName);
        jButton2.addActionListener(actionListener2);
        JPanel tmp = new JPanel();
        tmp.add(jButton1);
        tmp.add(jButton2);
        information.add(tmp);
    }

    public void toPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for(JPanel tmp : information){
            this.add(tmp);
        }

    }
}
