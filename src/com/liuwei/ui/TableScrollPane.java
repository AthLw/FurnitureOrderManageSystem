package com.liuwei.ui;

import com.liuwei.interfaces.BaseDataLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * @ClassName TableScrollPane
 * @Description TODO
 * @Author AthLw
 * @Date 14:00 2019/5/28
 * @Version 1.0
 **/
public class TableScrollPane extends JScrollPane {

        private BaseDataLoader dataLoader;
        private boolean enable = true;

        public TableScrollPane(Component view) {
            super(view);
            this.getVerticalScrollBar().addAdjustmentListener( e ->{
                if (enable) {
                    if (dataLoader != null) {
                        JScrollBar bar = (JScrollBar) e.getSource();
                        int max = bar.getMaximum();
                        int extent = bar.getModel().getExtent();
                        int value = bar.getValue();
                        //判断滚动条是否拉到底部
                        if ((max - (value + extent)) < 4) {
                            //设置鼠标在滚动面板上的光标为等待状态
                            TableScrollPane.this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
                            new Task().execute();
                        }
                    }
                }
            });

        }

        /**
         * 设置一个数据的加载器
         * @param dataLoader
         */
        public void setDataLoader(BaseDataLoader dataLoader) {
            this.dataLoader = dataLoader;
        }

        /**
         * 任务类，用来加载数据
         */
        private class Task extends SwingWorker<Void, Void> {

            @Override
            protected Void doInBackground() throws Exception {
                enable = false;
                Thread.sleep(100);//增强加载效果，停留两秒
                dataLoader.nextBatch();
                return null;
            }

            @Override
            protected void done() {
                //设置滚动面板设置上鼠标设置为默认光标
                TableScrollPane.this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                enable = true;
            }
        }
}
