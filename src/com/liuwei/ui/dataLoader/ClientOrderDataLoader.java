package com.liuwei.ui.dataLoader;

import com.liuwei.business.ClientService;
import com.liuwei.business.OrderService;
import com.liuwei.entity.Order;

import javax.print.attribute.standard.OrientationRequested;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ClientOrderDataLoader
 * @Description TODO
 * @Author AthLw
 * @Date 21:00 2019/5/28
 * @Version 1.0
 **/
public class ClientOrderDataLoader extends TableDataLoader {
    private ClientService clientService;
    private int clientID;
    private List<Order> mylist;

    public ClientOrderDataLoader(DefaultTableModel model, int clientID) {
        this(model, 0, 50);
        clientService = new ClientService();
        this.clientID = clientID;
        mylist = new ArrayList<>();
    }

    ClientOrderDataLoader(DefaultTableModel model, int page, int number) {
        super(model, page, number);
        clientService = new ClientService();
    }

    @Override
    public void initTableData() {
        this.nextBatch();
    }

    @Override
    public void nextBatch() {
        DefaultTableModel model = this.getModel();

        int page = this.getPage();
        int number = this.getNumber();
        int rowcount = model.getRowCount();
        //System.out.println(preRowCount + "   " + rowcount);

        List<Order> list = clientService.queryOrder(clientID);
        if(!listIsEqual(list)){
            rowcount = 0;
            mylist = list;
            model.setNumRows(0);
            for (Order order : list) {
                model.insertRow(rowcount, order.toStringArray());
                rowcount++;
            }
        }

        super.nextBatch();
    }

    public boolean listIsEqual(List<Order> newList){
        if(newList.size() != mylist.size()){
            return false;
        }
        for(int i = 0; i < newList.size(); i++){
            if(!mylist.get(i).equals(newList.get(i))){
                return false;
            }
        }
        return true;
    }
}
