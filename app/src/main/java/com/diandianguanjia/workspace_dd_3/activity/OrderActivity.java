package com.diandianguanjia.workspace_dd_3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.adapter.RecycleViewOrderDetailAdapter;
import com.diandianguanjia.workspace_dd_3.base.BaseActivity;
import com.diandianguanjia.workspace_dd_3.base.BaseApi;
import com.diandianguanjia.workspace_dd_3.mode.OrderDetialsMode;
import com.diandianguanjia.workspace_dd_3.mode.ServiceMode;
import com.diandianguanjia.workspace_dd_3.utils.IntentUtils;
import com.diandianguanjia.workspace_dd_3.utils.MyActivityManager;
import com.diandianguanjia.workspace_dd_3.utils.ProgressUtils;
import com.diandianguanjia.workspace_dd_3.utils.ToastUitl;
import com.diandianguanjia.workspace_dd_3.utils.UtilPreference;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.duduhuo.dialog.smartisan.CustomizedDialog;
import cc.duduhuo.dialog.smartisan.SmartisanDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by an on 2017/8/29.
 */

public class OrderActivity extends BaseActivity implements View.OnClickListener {

    private String orderId;
    private String money;

    @Override
    public int getLayoutId() {
        return R.layout.activity_orderdetail;
    }

     private static final String TAG = "OrderActivity";
    @BindView(R.id.backBtn)
    Button backBtn;
    @BindView(R.id.tv_header)
    TextView tvtitle;
    @BindView(R.id.bt_header_right)
    Button tvRight;

    @BindView(R.id.order_detail_title)//下单名目
    TextView orderTitle;
    @BindView(R.id.order_detail_status)//项目状态
    TextView orderStatus;
    @BindView(R.id.order_detail_time01)//下单时间
    TextView orderTime;
    @BindView(R.id.order_detail_name)//下单人
    TextView orderName;
    @BindView(R.id.order_detail_phone)//联系电话
    TextView orderPhone;
    @BindView(R.id.order_detail_address)//服务地址
    TextView orderAddress;
    @BindView(R.id.order_detail_servies02)//服务商
    TextView orderServies;
    @BindView(R.id.order_detail_serviestype02)//服务商类型
    TextView orderServStype;
    @BindView(R.id.order_detail_RequireDescription01)//要求标准
    TextView orderRequireDesc;
    @BindView(R.id.order_detail_NormDescription01)//服务标准
    TextView orderNormDesc;
    @BindView(R.id.order_detail_serviessum)//订单金额
    TextView orderMoney;
    @BindView(R.id.order_detail_assign)//指派
    Button orderAssign;

    @BindView(R.id.order_detail_serviephone)//服务商电话
    TextView ordderServiePhone;
    @BindView(R.id.order_detail_canncel)//取消//
    Button orderCanncel;
   @BindView(R.id.order_btn_sure_sum)//确认金额
    Button orderSureSum;


    @BindView(R.id.lin_order)
    LinearLayout linOrder;
    @BindView(R.id.order_detail_self)
    Button orderDetailSelf;

    private List<ServiceMode> modeLists;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_orderdetail);

        ButterKnife.bind(this);
        MyActivityManager.getInstance().addActivity(this);
        tvtitle.setText("订单详情");

        backBtn.setOnClickListener(this);
        orderAssign.setOnClickListener(this);
        orderCanncel.setOnClickListener(this);
        orderAddress.setOnClickListener(this);
        orderPhone.setOnClickListener(this);
        ordderServiePhone.setOnClickListener(this);
        orderSureSum.setOnClickListener(this);
        orderDetailSelf.setOnClickListener(this);

        showProgress("加载中...");
        disShowProgress();


        orderId = getIntent().getBundleExtra("bundle").getString("orderId");
        String status = getIntent().getBundleExtra("bundle").getString("status");
        String statice = getIntent().getBundleExtra("bundle").getString("statice");
        money = getIntent().getBundleExtra("bundle").getString("money");



        orderMoney.setText("￥ "+money);

        //状态：0已取消，1已下单，2服务中，3已支付，4已完成，5待用户确认


        if (!TextUtils.isEmpty(statice)) {
                if (statice.equals("statice")) {
                    orderStatus.setText("已完成");
                }

        } else if ("4".equals(status)){
            orderStatus.setText("已完成");

        }else if ("0".equals(status)){
            orderStatus.setText("已取消");
            orderMoney.setText("");
        }
        else if ("5".equals(status)){
            orderStatus.setText("待确认");

        }else if ("2".equals(status)){
            orderStatus.setText("服务中");
            //orderSureSum.setVisibility(View.VISIBLE);


        } else if ("1".equals(status)){
            orderStatus.setText("已下单");
            //linOrder.setVisibility(View.VISIBLE);

        }
        else if ("3".equals(status)){
            orderStatus.setText("已支付");
        }



     gteOrderDetail(orderId);

    }

    /**
     * 根据订单id获取有关订单的所有信息  TODO
     * @param orderId
     */
    private void gteOrderDetail(String orderId) {

        ProgressUtils.createProgressDialogTitle(this,"加载中....");

        BaseApi.getApiService().getOrderDetials(UtilPreference.getStringValue(this,"jkey"),UtilPreference.getStringValue(this,"captain_id"),orderId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderDetialsMode>() {
                    @Override
                    public void onCompleted() {
                        ProgressUtils.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OrderDetialsMode orderDetialsMode) {

                        OrderDetialsMode.MessageBean message = orderDetialsMode.getMessage();


                        Log.i(TAG, "----onNext: "+message.getUser_name());

                        orderTitle.setText("服务项目："+message.getServ());
                        orderTime.setText(""+message.getC_time());
                        orderName.setText("用户名："+message.getUser_name());
                        orderPhone.setText("联系电话："+message.getUser_phone());
                        orderAddress.setText("服务地址："+message.getProvince()+message.getCity()+message.getDistrict()+message.getAddress());
                        ordderServiePhone.setText("创客电话：");
                        orderRequireDesc.setText(message.getNeed_serv());
                        orderNormDesc.setText(message.getServ_desc());

                        //orderMoney.setText(String.format(this.getResources().getString(R.string.heji_yuan), Double.valueOf(mMoney) / 1.0f));



                    }
                });


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.backBtn:
                finish();
                break;
            case R.id.order_detail_assign://指派订单

                Bundle bundle=new Bundle();
                bundle.putString("orderId",orderId);
                IntentUtils.startActivityBundle(this,AssaignActivity.class,bundle);

                //assignOrderMethod();
                break;
            case R.id.order_detail_canncel://取消订单
                MyActivityManager.getInstance().removeActivity(OrderActivity.this);
                break;
            case R.id.order_detail_address://服务地址
                break;
            case R.id.order_detail_phone://下单人联系电话
                break;
            case R.id.order_detail_serviephone://服务商联系电话
                break;
            case R.id.order_btn_sure_sum://确认金额
                Intent intent=new Intent(this,SureOrderDescActivity.class);
                intent.putExtra("orderId",orderId);
                startActivityForResult(intent,100);
                break;
            case R.id.order_detail_self://承接
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && resultCode==101){
            String sum = data.getStringExtra("sum");
            String desc = data.getStringExtra("desc");

            orderNormDesc.setText(desc);
            orderMoney.setText(String.format(getResources().getString(R.string.heji_yuan), Double.valueOf(sum) / 1.0f));
            orderSureSum.setVisibility(View.INVISIBLE);

        }
    }

    /**
     * 指派订单
     */
    private void assignOrderMethod() {
        final CustomizedDialog customizedDialog = SmartisanDialog.createCustomizedDialog(this);

        View view = View.inflate(this, R.layout.item_dialog_assign, null);
        RecyclerView recyxle= (RecyclerView) view.findViewById(R.id.order_recycle);

        recyxle.setOnClickListener(this);

        recyxle.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewOrderDetailAdapter adapter=new RecycleViewOrderDetailAdapter(modeLists);
        recyxle.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Log.i(TAG, "onItemClick: "+position);
                final ServiceMode mode = modeLists.get(position);

                final CustomizedDialog normalDialog = SmartisanDialog.createCustomizedDialog(OrderActivity.this);
                normalDialog.setRightBtnText("确定");
                normalDialog.setLeftBtnText("取消");
                normalDialog.setTitle("确定指派订单 ？");

                final View normalView = View.inflate(OrderActivity.this, R.layout.layout_item_order01, null);

                 TextView tvCode= (TextView) normalView.findViewById(R.id.service_code);
                 TextView tvCompany= (TextView) normalView.findViewById(R.id.service_company);
                 TextView tvAddress= (TextView) normalView.findViewById(R.id.service_address);
                 TextView tvName= (TextView) normalView.findViewById(R.id.service_name);

                tvCode.setText("代号："+mode.getServiceCode());
                tvCompany.setText("公司："+mode.getServiceCompany());
                tvAddress.setText("地址："+mode.getServiceAddress());
                tvName.setText("负责人："+mode.getServiceName());
                normalDialog.addView(normalView);

                normalDialog.setOnSelectListener(new CustomizedDialog.OnSelectListener() {
                    @Override
                    public void onLeftSelect() {
                        normalDialog.dismiss();

                    }

                    @Override
                    public void onRightSelect() {

                        ToastUitl.showShort(""+mode.getServiceName());
                        normalDialog.dismiss();


                        customizedDialog.dismiss();

                        postOrderAssign(mode);

                    }
                });

                normalDialog.show();


            }
        });


        customizedDialog.addView(view);
        customizedDialog.setTitle("指派订单:");
        customizedDialog.show();


    }

    /**
     * 指派成功上传服务器
     * @param mode
     */
    private void postOrderAssign(ServiceMode mode) {


        finish();

    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

            if (keyCode==KeyEvent.KEYCODE_BACK){
                finish();
                return true;
            }

        return super.onKeyUp(keyCode, event);
    }
}
