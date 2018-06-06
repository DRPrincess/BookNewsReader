package com.south.worker.ui;


/**
 * 
 *描述   ：
 * 
 *作者   ：Created by DuanRui on 2018/5/31.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void showTipDialog(String message);
    void showToast(String message);
}
