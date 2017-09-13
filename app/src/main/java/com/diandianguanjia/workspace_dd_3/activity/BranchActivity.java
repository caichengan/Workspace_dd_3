package com.diandianguanjia.workspace_dd_3.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.adapter.BranchAdapter;
import com.diandianguanjia.workspace_dd_3.base.BaseActivity;
import com.diandianguanjia.workspace_dd_3.mode.AroundServerBean;
import com.diandianguanjia.workspace_dd_3.utils.MyActivityManager;
import com.diandianguanjia.workspace_dd_3.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by an on 2017/8/31.
 */

public class BranchActivity extends BaseActivity implements View.OnClickListener {


    List<AroundServerBean> modeLists;
    @BindView(R.id.header_line)
    View headerLine;
    @BindView(R.id.backBtn)
    Button backBtn;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.bt_header_right)
    Button btHeaderRight;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.branch_recycle)
    RecyclerView branchRecycle;
    @BindView(R.id.branch_refreshLayout)
    RefreshLayout branchRefreshLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_branch;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        modeLists = new ArrayList<>();

        tvHeader.setText("旗下网点");

        modeLists = getModeLists();

        MyActivityManager.getInstance().addActivity(this);

        backBtn.setOnClickListener(this);

        initDatas();

        BranchAdapter adapter = new BranchAdapter(this, modeLists);

        branchRecycle.setAdapter(adapter);

        branchRecycle.setLayoutManager(new LinearLayoutManager(branchRecycle.getContext()));


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


            }
        });

    }




    private static final String TAG = "BranchActivity";

    public List<AroundServerBean> getModeLists() {



        Log.i(TAG, "getModeLists: ");

        for (int j = 0; j < 20; j++) {
            AroundServerBean mode = new AroundServerBean();

            mode.setId(123+"");
            mode.setAddress("输入法是客人看附件");
            mode.setBoss_name("张三");

            modeLists.add(mode);
        }
        return modeLists;
    }

    private void initDatas() {
        branchRefreshLayout.setColorSchemeColors(Color.RED, Color.RED);
        //设置下拉刷新监听器
        branchRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(BranchActivity.this, "refresh", Toast.LENGTH_SHORT).show();
                branchRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //加载新数据
                        //adapter.notifyDataSetChanged();
                        //加载完调用该方法
                        branchRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        //设置上拉监听器
        branchRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {

                branchRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //上拉加载数据
                       /* datas.add(System.currentTimeMillis() + "");
                        adapter.notifyDataSetChanged();
                        branchRefresh.setLoading(false);*/


                    }
                }, 1500);

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.backBtn:
                MyActivityManager.getInstance().removeActivity(this);
                break;
        }

    }
}
