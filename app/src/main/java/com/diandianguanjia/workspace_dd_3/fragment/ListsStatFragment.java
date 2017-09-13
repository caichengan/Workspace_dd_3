package com.diandianguanjia.workspace_dd_3.fragment;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.activity.OrderActivity;
import com.diandianguanjia.workspace_dd_3.adapter.RecycleViewMainAdapter;
import com.diandianguanjia.workspace_dd_3.adapter.RecycleViewStaticAdapter;
import com.diandianguanjia.workspace_dd_3.base.BaseApi;
import com.diandianguanjia.workspace_dd_3.mode.Datas;
import com.diandianguanjia.workspace_dd_3.mode.OrderStatMode;
import com.diandianguanjia.workspace_dd_3.mode.OrderStatMode;
import com.diandianguanjia.workspace_dd_3.mode.OrderStatMode;
import com.diandianguanjia.workspace_dd_3.utils.DateUtil;
import com.diandianguanjia.workspace_dd_3.utils.IntentUtils;
import com.diandianguanjia.workspace_dd_3.utils.ProgressUtils;
import com.diandianguanjia.workspace_dd_3.utils.TimeUtil;
import com.diandianguanjia.workspace_dd_3.utils.ToastUitl;
import com.diandianguanjia.workspace_dd_3.utils.UtilPreference;
import com.diandianguanjia.workspace_dd_3.view.RefreshLayout;
import com.diandianguanjia.workspace_dd_3.view.RxBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by an on 2017/7/31.
 */

public class ListsStatFragment extends Fragment {

    private View view;
    private RecycleViewStaticAdapter homeAdapter;

    private int mCurrentCounter=0;
    private int TOTAL_COUNTER=0;
    private boolean isErr=false;

    private List<OrderStatMode.MessageBean.DataBean> message=new ArrayList<>();
    private int year;
    private int month;
    private int day;
    private double totalMoney;
    private TextView totalText;

    public static ListsStatFragment getInstance(String style, String statice){

        ListsStatFragment listsFragment = new ListsStatFragment();

        Bundle args = new Bundle();

        args.putString("style", style);
        args.putString("statice", statice);

        listsFragment.setArguments(args);

        return listsFragment;
    }

    private static final String TAG = "ListsFragment";
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private LinearLayout lin;
    private String style;
    private String statice;

    private String beginTime="";
    private String endTime="";
    public ListsStatFragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        style= arguments.getString("style");
        statice=arguments.getString("statice");


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_lists, null);

        totalText = (TextView) view.findViewById(R.id.totalMoney);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycle);

        refreshLayout= (RefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUitl.showShort("刷新完成");
                refreshLayout.setRefreshing(true);
                refreshLayout.setRefreshing(false);
            }
        });


        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "---------onResume: 进入一个新的fragment=--"+style);


        recyclerView.setVisibility(View.VISIBLE);
        Log.i(TAG, "----onResume: "+TimeUtil.getCurrentDay());

       /* getListDatastyle(style);*/

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //判断Fragment中的ListView时候存在，判断该Fragment时候已经正在前台显示  通过这两个判断，就可以知道什么时候去加载数据了
        if (isVisibleToUser && isVisible()) {
            getListDatastyle(style); //加载数据的方法
            Log.i(TAG, "setUserVisibleHint:88 ");
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getUserVisibleHint()) {
            getListDatastyle(style);
            Log.i(TAG, "setUserVisibleHint: ");
        }
        super.onActivityCreated(savedInstanceState);
    }



    /**
     * 获取统计模块的订单
     * @param style
     */
    private void getListDatastyle(final String style) {

        message.clear();
        mCurrentCounter=0;

        Calendar c = Calendar.getInstance();
         year = c.get(Calendar.YEAR);    //获取年
        Date time = c.getTime();

        /**
         * 获取当天时间0点和24点
         */
        Date timesmorning = DateUtil.getTimesmorning();
        Date timesnight = DateUtil.getTimesnight();

        /**
         * 获取当前日期本周的第一天
         */
        Date firstDayOfWeek = DateUtil.getFirstDayOfWeek(time);
        Date lastDayOfWeek = DateUtil.getLastDayOfWeek(time);
        /**
         * 获取指定日期月的第一天
         */
        Date firstDayOfMonth = DateUtil.getFirstDayOfMonth(time);
        Date lastDayOfMonth = DateUtil.getLastDayOfMonth(time);

        /**
         * 获取指定日期季度的第一天
         */
        Date firstDayOfQuarter = DateUtil.getFirstDayOfQuarter(time);
        Date lastDayOfQuarter = DateUtil.getLastDayOfQuarter(time);

        Log.i(TAG, "-----currentDay: ---"+ TimeUtil.getStringByFormat(time,"yyyy-MM-dd"));//2017-09-08 17:28
        Log.i(TAG, "-----firstDayOfWeek: ---"+ TimeUtil.getStringByFormat(firstDayOfWeek,"yyyy-MM-dd HH:mm"));//2017-09-08 17:28
        Log.i(TAG, "-----firstDayOfMonth: ---"+ TimeUtil.getStringByFormat(firstDayOfMonth,"yyyy-MM-dd"));//2017-09-08 17:28
        Log.i(TAG, "-----firstDayOfQuarter: ---"+ TimeUtil.getStringByFormat(firstDayOfQuarter,"yyyy-MM-dd"));//2017-09-08 17:28

        if (style.startsWith("每年")){

            beginTime=""+year+"-1-1";
            endTime=""+year+1+"-12-30";

        }else if (style.startsWith("每季")){
            beginTime=TimeUtil.getStringByFormat(firstDayOfQuarter,"yyyy-MM-dd");
            endTime=TimeUtil.getStringByFormat(lastDayOfQuarter,"yyyy-MM-dd");

        }else if (style.startsWith("每月")){

            beginTime=TimeUtil.getStringByFormat(firstDayOfMonth,"yyyy-MM-dd");
            endTime=TimeUtil.getStringByFormat(lastDayOfMonth,"yyyy-MM-dd");

        }else if (style.startsWith("每周")){

            beginTime=TimeUtil.getStringByFormat(firstDayOfWeek,"yyyy-MM-dd");
            endTime=TimeUtil.getStringByFormat(lastDayOfWeek,"yyyy-MM-dd");
        }else if (style.startsWith("每天")){

            beginTime=TimeUtil.getStringByFormat(timesmorning,"yyyy-MM-dd HH:mm:ss");
            endTime=TimeUtil.getStringByFormat(timesnight,"yyyy-MM-dd HH:mm:ss");
        }

            Log.i(TAG, "---beginTime:--- "+beginTime+"----"+endTime);


            ProgressUtils.createProgressDialogTitle(getActivity(),"加载中...");
            BaseApi.getApiService().getOrderStaticDatas(UtilPreference.getStringValue(getActivity(),"jkey"),UtilPreference.getStringValue(getActivity(),"captain_id"),beginTime,endTime,"4",mCurrentCounter).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<OrderStatMode, OrderStatMode>() {

                        @Override
                        public OrderStatMode call(OrderStatMode orderMode) {
                            return orderMode;
                        }
                    }).subscribe(new Subscriber<OrderStatMode>() {
                @Override
                public void onCompleted() {
                    ProgressUtils.dismissProgressDialog();
                }
                @Override
                public void onError(Throwable e) {
                    ProgressUtils.dismissProgressDialog();
                }
                @Override
                public void onNext(OrderStatMode orderMode) {

                    totalMoney = orderMode.getMessage().getSum();

                    message = orderMode.getMessage().getData();
                    mCurrentCounter+=10;
                    homeAdapter = new RecycleViewStaticAdapter(getActivity(),message);
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    homeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                    Log.i(TAG, "---------size:=--"+homeAdapter.getData().size());
                    homeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                    homeAdapter.setPreLoadNumber(1);

                    recyclerView.setAdapter(homeAdapter);

                  /*  if (homeAdapter.getData().size()==0){
                        recyclerView.setVisibility(View.GONE);

                    }*/

                    RxBus.getInstance().post(new Datas(style,totalMoney));

                    initLoad(style);


                }
            });

    }

    private void initLoad(final String status) {

      if (status.startsWith("每年")) {
          TOTAL_COUNTER = Integer.parseInt(UtilPreference.getStringValue(getActivity(), "yearNum"));
      }else if (status.startsWith("每季")) {
          TOTAL_COUNTER=Integer.parseInt(UtilPreference.getStringValue(getActivity(),"quarterNum"));
      }else if (status.startsWith("每月")) {
          TOTAL_COUNTER=Integer.parseInt(UtilPreference.getStringValue(getActivity(),"monthNum"));
      }else if (status.startsWith("每周")) {
          TOTAL_COUNTER=Integer.parseInt(UtilPreference.getStringValue(getActivity(),"weekNum"));
      }else if (status.startsWith("每天")) {
          TOTAL_COUNTER=Integer.parseInt(UtilPreference.getStringValue(getActivity(),"dauNum"));
      }


        /**
         * 上拉加载
         */
        homeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= TOTAL_COUNTER) {
                            //数据全部加载完毕
                            homeAdapter.loadMoreEnd();

                            isErr=true;

                        } else {
                            if (!isErr) {
                                //成功获取更多数据

                                getMoreOrderLoad(status);



                            } else {
                                //获取更多数据失败
                                isErr = true;
                                homeAdapter.loadMoreFail();

                            }
                        }
                    }

                }, 1000);
            }

        }, recyclerView);


        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                OrderStatMode.MessageBean.DataBean messageBean = message.get(position);

                ToastUitl.showShort("-----"+position);
                Bundle bundle=new Bundle();
                bundle.putString("orderId",messageBean.getId());
                bundle.putString("status",messageBean.getStatus());
                bundle.putString("statice",statice);
                IntentUtils.startActivityBundle(getActivity(),OrderActivity.class,bundle);
            }
        });

    }

    private void getMoreOrderLoad(String status) {

        BaseApi.getApiService().getOrderStaticDatas(UtilPreference.getStringValue(getActivity(),"jkey"),UtilPreference.getStringValue(getActivity(),"captain_id"),beginTime,endTime,"4",mCurrentCounter).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<OrderStatMode, OrderStatMode>() {

                    @Override
                    public OrderStatMode call(OrderStatMode orderMode) {
                        return orderMode;
                    }
                }).subscribe(new Subscriber<OrderStatMode>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(OrderStatMode orderMode) {
                List<OrderStatMode.MessageBean.DataBean> loadMesssta = orderMode.getMessage().getData();
                homeAdapter.addData(loadMesssta);
                mCurrentCounter +=10;

                Log.i(TAG, "--run:-- "+mCurrentCounter);
                homeAdapter.loadMoreComplete();


            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
