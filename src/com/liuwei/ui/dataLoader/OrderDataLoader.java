package com.liuwei.ui.dataLoader;

import com.liuwei.business.OrderService;
import com.liuwei.entity.Order;
import com.liuwei.ui.dataLoader.TableDataLoader;

import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * @ClassName OrderDataLoader
 * @Description TODO
 * @Author AthLw
 * @Date 14:00 2019/5/28
 * @Version 1.0
 **/
public class OrderDataLoader extends TableDataLoader {
    private OrderService orderService;

    public OrderDataLoader(DefaultTableModel model) {
        this(model, 0, 50);
        orderService = new OrderService();
    }

    OrderDataLoader(DefaultTableModel model, int page, int number) {
        super(model, page, number);
        orderService = new OrderService();
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

        List<Order> list = orderService.query(page * number);
        for (Order order : list) {
            model.insertRow(rowcount, order.toStringArray());
            rowcount++;
        }

        super.nextBatch();
    }
}
