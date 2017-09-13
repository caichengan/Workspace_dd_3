package com.diandianguanjia.workspace_dd_3.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.diandianguanjia.workspace_dd_3.mode.OrderStatMode;

import java.util.List;

/**
 * Created by an on 2017/8/28.
 */

public class MainOrderAdapter extends BaseQuickAdapter<OrderStatMode,BaseViewHolder> {


    List<OrderStatMode> listDatas;

    public MainOrderAdapter(int layoutResId, List data) {
        super(layoutResId, data);

        this.listDatas=data;
    }

    public MainOrderAdapter(List data) {
        super(data);
        this.listDatas=data;
    }

    public MainOrderAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, OrderStatMode item) {

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }
}
