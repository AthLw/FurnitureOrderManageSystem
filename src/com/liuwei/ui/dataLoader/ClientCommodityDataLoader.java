package com.liuwei.ui.dataLoader;

import com.liuwei.business.ClientService;
import com.liuwei.entity.Commodity;

import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * @ClassName CommodityDataLoader
 * @Description TODO
 * @Author AthLw
 * @Date 19:00 2019/5/28
 * @Version 1.0
 **/
public class ClientCommodityDataLoader extends TableDataLoader {
    private ClientService clientService;

    public ClientCommodityDataLoader(DefaultTableModel model) {
        this(model, 0, 50);
        clientService = new ClientService();
    }

    ClientCommodityDataLoader(DefaultTableModel model, int page, int number) {
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
        //int page = this.getPage();
        //int number = this.getNumber();
        int rowcount = model.getRowCount();

        List<Commodity> list = clientService.queryCommodity();
        for (Commodity commodity : list) {
            model.insertRow(rowcount, commodity.toStringArray());
            rowcount++;
        }

        super.nextBatch();
    }
}
