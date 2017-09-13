package com.diandianguanjia.workspace_dd_3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.base.BaseApi;
import com.diandianguanjia.workspace_dd_3.mode.NotJkey;
import com.diandianguanjia.workspace_dd_3.mode.Result;
import com.diandianguanjia.workspace_dd_3.utils.MyActivityManager;
import com.diandianguanjia.workspace_dd_3.utils.ToastUitl;
import com.diandianguanjia.workspace_dd_3.utils.UtilPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by an on 2017/9/2.
 */

public class SureOrderDescActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.backBtn)
    Button backBtn;
    @BindView(R.id.tv_header)
    TextView tvtitle;
    @BindView(R.id.bt_header_right)
    Button tvRight;

    @BindView(R.id.order_detail_edsum)
    EditText orderEditSum;
    @BindView(R.id.order_detail_eddesc)
    EditText orderEditDesc;
    @BindView(R.id.order_detail_sure)
    Button orderSure;
    private String orderId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_item_sure_sum);
        ButterKnife.bind(this);
        MyActivityManager.getInstance().addActivity(this);


        backBtn.setOnClickListener(this);

        orderId = getIntent().getStringExtra("orderId");

        orderSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sureOrderMoney();


            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    private void sureOrderMoney() {
        String editSum = orderEditSum.getText().toString();
        String editDesc = orderEditDesc.getText().toString();

        if (TextUtils.isEmpty(editDesc)){

            ToastUitl.showShort("请输入服务标准");
            return;

        }
        if (TextUtils.isEmpty(editSum)){
            ToastUitl.showShort("请输入价格");
            return;
        }

        Intent intent=new Intent(this,SureOrderDescActivity.class);

        intent.putExtra("sum",editSum);
        intent.putExtra("desc",editDesc);
        setResult(101,intent);

        postSureOrderSum(orderId,editSum,editDesc);
        finish();



    }

    /**
     * 上传确认订单价格服务标准
     * @param orderId
     * @param editSum
     * @param editDesc
     */
    private void postSureOrderSum(String orderId, String editSum, String editDesc) {



            /**
             * 确定服务标准价格
             */
        BaseApi.getApiService().postOrderSum(UtilPreference.getStringValue(this,"jkey"),34,"orderId","editSum","editDesc").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result<NotJkey>>() {
                        @Override
                        public void onCompleted() {

                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Result<NotJkey> orderNumberResult) {




                        }
                    });

        }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backBtn:
               MyActivityManager.getInstance().removeActivity(this);
                break;
        }

    }
}
