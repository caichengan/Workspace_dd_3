package com.diandianguanjia.workspace_dd_3.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.mode.OrderMainMode;
import com.diandianguanjia.workspace_dd_3.mode.OrderStatMode;

import java.util.List;


/**
 * Created by an on 2017/7/31.
 */

public class RecycleViewStaticAdapter extends BaseQuickAdapter<OrderStatMode.MessageBean.DataBean,BaseViewHolder> {

    private Context mCOntext;
    public RecycleViewStaticAdapter(Context mCOntext, List<OrderStatMode.MessageBean.DataBean> modeLists) {


        super( R.layout.layout_item_main, modeLists);
        this.mCOntext=mCOntext;
    }


//

    @Override
    protected void convert(BaseViewHolder helper, OrderStatMode.MessageBean.DataBean item) {


        //helper.setText(R.id.orderTitle,item.getServ_name());
        helper.setText(R.id.orderName,item.getUser_name());
        helper.setText(R.id.orderMoney,"￥"+item.getSum());
       // helper.setText(R.id.orderMoney,String.format(mCOntext.getResources().getString(R.string.heji_yuan), Double.valueOf(item.getSum()) / 1.0f));
        helper.setText(R.id.orderPhone,item.getUser_phone());
        helper.setText(R.id.orderTime,item.getC_time());

        //	mHeJiTV.setText(String.format(getResources().getString(R.string.heji_yuan), mMoneyTotal / 100.0f));

        String mStyle = item.getStatus();

        //状态：0已取消，1已下单，2服务中，3已支付，4已完成，5待用户确认

        if (mStyle.equals("0")){
            helper.setText(R.id.orderStyle,"已取消");
        }else if (mStyle.equals("1")){
            helper.setText(R.id.orderStyle,"已下单");
            }else if (mStyle.equals("2")){
            helper.setText(R.id.orderStyle,"服务中");
        }
        else if (mStyle.equals("3")){
            helper.setText(R.id.orderStyle,"已支付");
        }else if (mStyle.equals("4")){
            helper.setText(R.id.orderStyle,"已完成");
        } else if (mStyle.equals("5")){
            helper.setText(R.id.orderStyle,"待确认");
        }



    }






}
