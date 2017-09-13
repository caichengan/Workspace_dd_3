package com.diandianguanjia.workspace_dd_3.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.adapter.FragmentAdapter;
import com.diandianguanjia.workspace_dd_3.base.BaseApi;
import com.diandianguanjia.workspace_dd_3.base.MyBaseFragment;
import com.diandianguanjia.workspace_dd_3.mode.Datas;
import com.diandianguanjia.workspace_dd_3.mode.Result11;
import com.diandianguanjia.workspace_dd_3.mode.StatisticsNumber;
import com.diandianguanjia.workspace_dd_3.utils.UtilPreference;
import com.diandianguanjia.workspace_dd_3.view.RxBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by an on 2017/8/28.
 *
 * 统计模块，统计以年、季、月、周为周期的订单量和金额
 */

public class StaticticsFragment extends MyBaseFragment {

    Button backBtn;
    TextView tvTitle;
    Button btnRight;
    TabLayout statTabs;
    ViewPager statViewpager;
    TextView stypeMoney;
    TextView totalMoney;
    private String yearNum;
    private String quarterNum;
    private String monthNum;
    private String weekNum;
    private String datNum;


    public static StaticticsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        StaticticsFragment fragment = new StaticticsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setContentView() {
        return R.layout.fragment_statictics;
    }




    @Override
    protected void lazyLoad() {



        initView();



        //initDatas();
        Log.i(TAG, "lazyLoad: ");

        RxBus.getInstance().toObservableSticky(Datas.class).subscribe(new Action1<Datas>() {
            @Override
            public void call(Datas datas) {
                double mMoney1 = datas.getmTotalMoney();
                String message = datas.getMessage();

                Log.i(TAG, "----------mMoney1--: "+mMoney1);


                stypeMoney.setText(message+"的订单量其总金额：");
                totalMoney.setText(String.format(getActivity().getResources().getString(R.string.heji_yuan), mMoney1 / 1.0f));
            }
        });


    }


        private void initDatas() {


            /**
             * 获取统计模块每个状态下订单数量
             */
            BaseApi.getApiService().getStatNumber(UtilPreference.getStringValue(getActivity(),"jkey"),UtilPreference.getStringValue(getActivity(),"captain_id")).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result11<StatisticsNumber>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Result11<StatisticsNumber> StatisticsNumResult) {

                            StatisticsNumber statisticsNumber = StatisticsNumResult.getMessage();

                            datNum=statisticsNumber.getDay();
                            weekNum = statisticsNumber.getWeek();
                            monthNum = statisticsNumber.getMonth();
                            quarterNum = statisticsNumber.getSeason();
                            yearNum = statisticsNumber.getYear();


                            Log.i(TAG, "---------statict---------"+monthNum+"---"+quarterNum+"---"+yearNum);

                            UtilPreference.saveString(getActivity(),"dauNum",datNum);
                            UtilPreference.saveString(getActivity(),"weekNum",weekNum);
                            UtilPreference.saveString(getActivity(),"monthNum",monthNum);
                            UtilPreference.saveString(getActivity(),"quarterNum",quarterNum);
                            UtilPreference.saveString(getActivity(),"yearNum",yearNum);

                        }
                    });

        }


    @Override
    public void onResume() {
        super.onResume();


        initDatas();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        RxBus.getInstance().removeAllStickyEvents();

    }

    private void initView() {
        backBtn=findViewById(R.id.backBtn);
        tvTitle=findViewById(R.id.tv_header);
        btnRight=findViewById(R.id.bt_header_right);
        backBtn.setVisibility(View.INVISIBLE);
        tvTitle.setText("统计");

        statTabs=findViewById(R.id.stat_tabs);
        statViewpager=findViewById(R.id.stat_viewpager);
        stypeMoney=findViewById(R.id.stypeMoney);
        totalMoney=findViewById(R.id.totalMoney);


        initViewPager();

    }
    private void initViewPager() {

        List<String> titles=new ArrayList<>();
        titles.add("每年"+UtilPreference.getStringValue(getActivity(),"yearNum"));
        titles.add("每季"+UtilPreference.getStringValue(getActivity(),"quarterNum"));
        titles.add("每月"+UtilPreference.getStringValue(getActivity(),"monthNum"));
        titles.add("每周"+UtilPreference.getStringValue(getActivity(),"weekNum"));
        titles.add("每天"+UtilPreference.getStringValue(getActivity(),"dauNum"));


        for (int i = 0; i < titles.size(); i++) {
            statTabs.addTab(statTabs.newTab().setText(titles.get(i)));
        }

        List<Fragment> fragments=new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            fragments.add( ListsStatFragment.getInstance(titles.get(i),"statice"));
        }

        FragmentAdapter fragmentPagerAdapter=new FragmentAdapter(getFragmentManager(),fragments,titles);


        //给viewpager设置适配器
        statViewpager.setAdapter(fragmentPagerAdapter);
        //将viewpager和Tablayout关联起来
        statTabs.setupWithViewPager(statViewpager);

        statTabs.setTabTextColors(Color.BLACK,Color.RED);

        //给tabLayout设置适配器
        statTabs.setTabsFromPagerAdapter(fragmentPagerAdapter);

    }

}
