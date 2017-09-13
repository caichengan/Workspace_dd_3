package com.diandianguanjia.workspace_dd_3.fragment;

import android.os.Bundle;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.activity.OrderActivity;
import com.diandianguanjia.workspace_dd_3.adapter.RecycleViewMainAdapter;
import com.diandianguanjia.workspace_dd_3.base.BaseApi;
import com.diandianguanjia.workspace_dd_3.mode.OrderMainMode;
import com.diandianguanjia.workspace_dd_3.mode.OrderStatMode;
import com.diandianguanjia.workspace_dd_3.utils.IntentUtils;
import com.diandianguanjia.workspace_dd_3.utils.ProgressUtils;
import com.diandianguanjia.workspace_dd_3.utils.ToastUitl;
import com.diandianguanjia.workspace_dd_3.utils.UtilPreference;
import com.diandianguanjia.workspace_dd_3.view.RefreshLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.R.attr.data;


/**
 * Created by an on 2017/7/31.
 */

public class ListsFragment extends Fragment {

    private View view;
    private RecycleViewMainAdapter homeAdapter;

    private int mCurrentCounter=0;
    private int TOTAL_COUNTER=0;
    private boolean isErr=false;
    private List<OrderMainMode.MessageBean> message=new ArrayList<>();

    public static ListsFragment getInstance(String style){

        ListsFragment listsFragment = new ListsFragment();

        Bundle args = new Bundle();

        args.putString("style", style);

        listsFragment.setArguments(args);

        return listsFragment;
    }

    private static final String TAG = "ListsFragment";
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private LinearLayout linearLayout;
    private String style;

    public ListsFragment() {
        super();
    }






    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        style= arguments.getString("style");





    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(getActivity(), R.layout.fragment_lists, null);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycle);

        Log.i(TAG, "-----onCreateView=--");

        refreshLayout= (RefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                refreshLayout.setRefreshing(false);
                ToastUitl.showShort("刷新成功");
            }
        });

//状态：0已取消，1已下单，2服务中，3已支付，4已完成，5待用户确认
        return view;
    }

    private void initLoad(final String status) {

        switch (status){
            case "0":

                TOTAL_COUNTER=Integer.parseInt(UtilPreference.getStringValue(getActivity(),"cancelNum"));
                break;
            case "1":
                TOTAL_COUNTER=Integer.parseInt(UtilPreference.getStringValue(getActivity(),"orderNum"));
                break;
            case "2":
                TOTAL_COUNTER=Integer.parseInt(UtilPreference.getStringValue(getActivity(),"serveNum"));
                break;
            case "3":
                TOTAL_COUNTER=Integer.parseInt(UtilPreference.getStringValue(getActivity(),"payNum"));
                break;
            case "4":
                TOTAL_COUNTER=Integer.parseInt(UtilPreference.getStringValue(getActivity(),"completeNum"));
                break;
            case "5":
                TOTAL_COUNTER=Integer.parseInt(UtilPreference.getStringValue(getActivity(),"toConfirmedNum"));
                break;
            default:
                TOTAL_COUNTER=Integer.parseInt(UtilPreference.getStringValue(getActivity(),"totalNum"));

                break;

        }


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

                                getModrOrderLoad(status);



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

                OrderMainMode.MessageBean messageBean = message.get(position);

                ToastUitl.showShort("-----"+position);

                Bundle bundle=new Bundle();
                bundle.putString("status",messageBean.getStatus());
                bundle.putString("money",messageBean.getSum());
                bundle.putString("orderId",messageBean.getId());
                bundle.putString("status",messageBean.getStatus());
                IntentUtils.startActivityBundle(getActivity(),OrderActivity.class,bundle);
            }
        });

    }

    /**
     * 获取更多数据
     * @param status
     */
    private void getModrOrderLoad(String status) {

        BaseApi.getApiService().getOrderDatas(UtilPreference.getStringValue(getActivity(),"jkey"),UtilPreference.getStringValue(getActivity(),"captain_id"),status,mCurrentCounter).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<OrderMainMode, OrderMainMode>() {

                    @Override
                    public OrderMainMode call(OrderMainMode orderMode) {
                        return orderMode;
                    }
                }).subscribe(new Subscriber<OrderMainMode>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(OrderMainMode orderMode) {


                List<OrderMainMode.MessageBean> message = orderMode.getMessage();
                homeAdapter.addData(message);
                mCurrentCounter +=10;

                Log.i(TAG, "--run:-- "+mCurrentCounter);
                homeAdapter.loadMoreComplete();


            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "---------onResume: 进入一个新的fragment=--"+style);
        if (style.startsWith("全部")){
            getAllOrderLists();
            style="全部";

        }else if (style.startsWith("已下单")){
            getModeLists("1");
            style="已下单";
        }else if (style.startsWith("待确认")){
            getModeLists("5");
            style="待确认";
        }else if (style.startsWith("已支付")){
            getModeLists("3");
            style="已支付";
        }else if (style.startsWith("服务中")){
            getModeLists("2");
            style="服务中";
        }else if (style.startsWith("已完成")){
            getModeLists("4");
            style="已完成";
        }else if (style.startsWith("已取消")){
            getModeLists("0");
            style="已取消";
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);





}

    public  List<OrderMainMode.MessageBean> getModeLists(final String status) {

        Log.i(TAG, "getModeLists: ");

        message.clear();
        mCurrentCounter=0;

        /**
         * @param  jkey(调用接口凭证)
         * @param  captain_id(社区店id)
         *  @param  status(订单状态)
         * @param  subscript(从第几条订单开始返回，下标)(选填)
         */

        ProgressUtils.createProgressDialogTitle(getActivity(),"加载中...");

         BaseApi.getApiService().getOrderDatas(UtilPreference.getStringValue(getActivity(),"jkey"),UtilPreference.getStringValue(getActivity(),"captain_id"),status,mCurrentCounter).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                 .map(new Func1<OrderMainMode, OrderMainMode>() {

                     @Override
                     public OrderMainMode call(OrderMainMode orderMode) {
                         return orderMode;
                     }
                 }).subscribe(new Subscriber<OrderMainMode>() {
             @Override
             public void onCompleted() {

                 ProgressUtils.dismissProgressDialog();

             }

             @Override
             public void onError(Throwable e) {
                 ProgressUtils.dismissProgressDialog();
                 ToastUitl.showShort("网络错误，请退出重试...");

             }

             @Override
             public void onNext(OrderMainMode orderMode) {


                 message = orderMode.getMessage();

                 mCurrentCounter=message.size();


                 homeAdapter = new RecycleViewMainAdapter(getActivity(),message);
                 recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                 homeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                 Log.i(TAG, "---------onActivityCreated:=--");
                 homeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                 homeAdapter.setPreLoadNumber(1);

                 recyclerView.setAdapter(homeAdapter);


                 initLoad(status);


             }
         });

        return message;



    }

    public  List<OrderMainMode.MessageBean> getAllOrderLists(){
        Log.i(TAG, "getModeLists: ");
        /**
         * @param  jkey(调用接口凭证)
         * @param  captain_id(社区店id)
         *  @param  status(订单状态)
         * @param  subscript(从第几条订单开始返回，下标)(选填)
         */
        mCurrentCounter+=10;

        BaseApi.getApiService().getOrderAllDatas(UtilPreference.getStringValue(getActivity(),"jkey"),UtilPreference.getStringValue(getActivity(),"captain_id"),mCurrentCounter).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<OrderMainMode, OrderMainMode>() {

                    @Override
                    public OrderMainMode call(OrderMainMode orderMode) {
                        return orderMode;
                    }
                }).subscribe(new Subscriber<OrderMainMode>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(OrderMainMode orderMode) {


                message = orderMode.getMessage();

                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                homeAdapter = new RecycleViewMainAdapter(getActivity(),message);

                homeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                Log.i(TAG, "---------onActivityCreated:=--");
                homeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                homeAdapter.setPreLoadNumber(1);

                recyclerView.setAdapter(homeAdapter);


                initLoad("");
            }
        });

        return message;


    }
}

