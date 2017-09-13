package com.diandianguanjia.workspace_dd_3.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.base.BaseActivity;
import com.diandianguanjia.workspace_dd_3.utils.MyActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by an on 2017/8/31.
 */

public class MyMessageActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.header_line)
    View headerLine;
    @BindView(R.id.backBtn)
    Button backBtn;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.bt_header_right)
    Button btHeaderRight;

    @BindView(R.id.ddhk_avatar)//店面图案
    ImageView ddhkAvatar;
    @BindView(R.id.rl_ddhk_servicesdetails_tv01)
    TextView rlDdhkServicesdetailsTv01;
    @BindView(R.id.rl_ddhk_province_city_address)//省市+具体地址
    Button rlDdhkAddress;
    @BindView(R.id.ddhk_location)//4S点地址
    LinearLayout ddhkLocation;
    @BindView(R.id.ddhk_name)//负责人姓名
    TextView ddhkName;
    @BindView(R.id.ddhk_phone)//联系电话
    TextView ddhkPhone;




    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        MyActivityManager.getInstance().addActivity(this);

        backBtn.setOnClickListener(this);
        ddhkLocation.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);



        tvHeader.setText("店面详情");

        rlDdhkServicesdetailsTv01.setText("粤T-401");
        rlDdhkAddress.setText("环境施工方会计师丢早上的回家吃饭看时间这就是大货车会计师的财富给继续讲");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.backBtn:
                MyActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.ddhk_location://跳转地址
                break;

        }
    }
}
