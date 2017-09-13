package com.diandianguanjia.workspace_dd_3.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.base.BaseActivity;
import com.diandianguanjia.workspace_dd_3.base.BaseApi;
import com.diandianguanjia.workspace_dd_3.mode.OrderDetialsMode;
import com.diandianguanjia.workspace_dd_3.utils.MyActivityManager;
import com.diandianguanjia.workspace_dd_3.utils.UtilPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by an on 2017/9/12.
 */

class AssaignActivity extends BaseActivity {


    @BindView(R.id.order_recycle)
    RecyclerView orderRecycle;
    private String orderId;

    @Override
    public int getLayoutId() {
        return R.layout.item_dialog_assign;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        MyActivityManager.getInstance().addActivity(this);

        orderId = getIntent().getBundleExtra("bundle").getString("orderId");

        get4SWebBranch();

    }

    /**
     * 获取4s旗下的网点
     */
    private void get4SWebBranch() {

        BaseApi.getApiService().getUser4SBranch(UtilPreference.getStringValue(this,"jkey"),UtilPreference.getStringValue(this,"captain_id")).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderDetialsMode>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OrderDetialsMode orderDetialsMode) {

                    }
                });



    }
}
