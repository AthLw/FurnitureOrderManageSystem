package com.liuwei.ui.dataLoader;


import com.liuwei.interfaces.BaseDataLoader;

import javax.swing.table.DefaultTableModel;

/**
 * @ClassName TableDateLoader
 * @Description TODO
 * @Author AthLw
 * @Date 14:00 2019/5/28
 * @Version 1.0
 **/

public abstract class TableDataLoader implements BaseDataLoader {
    private DefaultTableModel model;//表模型
    private int page;//从第几页开始
    private int number;//每次加载的数量

    /**
     * 构造方法，只需传入表模型参数，page为0，number为50
     * @param model
     */
    TableDataLoader(DefaultTableModel model) {
        this(model, 0, 50);
    }

    /**
     * 构造方法
     * @param model 表模型
     * @param page 从第几页开始的索引
     * @param number 每次加载的数量
     */
    TableDataLoader(DefaultTableModel model, int page, int number) {
        this.model = model;
        this.page = page;
        this.number = number;
    }

    /**
     * 初始化表数据
     */
    abstract void initTableData();

    @Override
    public void nextBatch() {
        page ++;
    }

    /**
     * 获取表模型
     * @return 表模型
     */
    public DefaultTableModel getModel() {
        return model;
    }

    /**
     * 获取从第几页加载
     * @return 索引
     */
    public int getPage() {
        return page;
    }

    /**
     * 获取每次加载数量
     * @return 加载数量
     */
    public int getNumber() {
        return number;
    }
}
