package com.diandianguanjia.workspace_dd_3.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.mode.ServiceMode;

import java.util.List;


/**
 * Created by an on 2017/7/31.
 *
 * 指派订单
 *
 */

public class RecycleViewOrderDetailAdapter extends BaseQuickAdapter<ServiceMode,BaseViewHolder> {

    public RecycleViewOrderDetailAdapter(List<ServiceMode> modeLists) {


        super( R.layout.layout_item_order, modeLists);

    }




    @Override
    protected void convert(BaseViewHolder helper, ServiceMode item) {
       // helper.setText(R.id.orderCircleImg,item.getServiceHead());
        helper.setText(R.id.service_code,"代号："+item.getServiceCode());
        helper.setText(R.id.service_company,"公司："+item.getServiceCompany());
        helper.setText(R.id.service_address,"地址："+item.getServiceAddress());
        helper.setText(R.id.service_name,item.getServiceName());
        helper.setText(R.id.service_phone,"电话："+item.getServicePhone());


    }






}
