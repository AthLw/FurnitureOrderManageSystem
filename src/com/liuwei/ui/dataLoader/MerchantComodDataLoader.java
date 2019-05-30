package com.liuwei.ui.dataLoader;

import com.liuwei.business.MerchantService;
import com.liuwei.entity.Commodity;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MerchantComodDataLoader
 * @Description TODO
 * @Author AthLw
 * @Date 19:00 2019/5/29
 * @Version 1.0
 **/
public class MerchantComodDataLoader extends TableDataLoader {
    private List<Commodity> commodityList;
    private MerchantService merchantService;
    private int merchantID;

    public MerchantComodDataLoader(DefaultTableModel tableModel, int merchantID){
        super(tableModel, 0, 50);
        commodityList = new ArrayList<>();
        merchantService = new MerchantService();
        this.merchantID = merchantID;
    }
    @Override
    void initTableData() {
        this.nextBatch();
    }


    public void setElement(int i, int j, String value){
        commodityList.get(i).setAttrib(j, value);
        merchantService.update(commodityList.get(i));
    }

    public void nextBatch(){
        commodityList = merchantService.queryCommodity(this.merchantID);
        DefaultTableModel model = this.getModel();
        model.setNumRows(0);    //flush
        int rowcount = 0;
        for(Commodity commodity: commodityList){
            model.insertRow(rowcount, commodity.toStringArray());
            rowcount++;
        }
    }

}
