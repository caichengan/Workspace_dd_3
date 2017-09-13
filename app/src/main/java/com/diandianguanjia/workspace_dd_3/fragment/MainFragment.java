package com.diandianguanjia.workspace_dd_3.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.activity.MainActivity;
import com.diandianguanjia.workspace_dd_3.adapter.FragmentAdapter;
import com.diandianguanjia.workspace_dd_3.adapter.MainOrderAdapter;
import com.diandianguanjia.workspace_dd_3.base.MyBaseFragment;
import com.diandianguanjia.workspace_dd_3.mode.OrderNumber;
import com.diandianguanjia.workspace_dd_3.mode.Result11;
import com.diandianguanjia.workspace_dd_3.net.ApiService;
import com.diandianguanjia.workspace_dd_3.utils.UtilPreference;
import com.diandianguanjia.workspace_dd_3.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.schedulers.Schedulers;


/**
 * Created by an on 2017/8/28.
 */

public class MainFragment extends MyBaseFragment {


    Button backBtn;
    TextView tvTitle;
    Button btnRight;

    MainActivity context;
    private ViewPager viewPager;
    private TabLayout tabs;
    private Toolbar toolbar;
    private AppBarLayout appBar;
    private CoordinatorLayout coordiation;
    RefreshLayout swipeRefreshLayout;
    RecyclerView recycle;
    private MainOrderAdapter adapter;
    private ApiService service;
    private String totalNum;
    private String orderNum;
    private String serveNum;
    private String payNum;
    private String completeNum;
    private String toConfirmedNum;
    private String cancelNum;


    public MainFragment() {
    }




    public static MainFragment getInstance() {

        Bundle args = new Bundle();


        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "-------onCreate:------ ");
        context= (MainActivity) getActivity();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://weixin.dd1760.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(ApiService.class);


       
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_main;
    }
    @Override
    protected void lazyLoad() {

        initView();

    }

    private void initView() {
        Log.i(TAG, "----------initView: --------");
        backBtn=findViewById(R.id.backBtn);
        tvTitle=findViewById(R.id.tv_header);
        btnRight=findViewById(R.id.bt_header_right);
        backBtn.setVisibility(View.INVISIBLE);
        tvTitle.setText("店长");
        recycle=findViewById(R.id.recycle);

        tabs= (TabLayout) findViewById(R.id.tabs);// app:tabMode="scrollable"可滑动的
        viewPager= (ViewPager) findViewById(R.id.viewpager);

        initViewPager();
    }
    private void initViewPager() {//7618952f045bf9e43de5fdb209975a03
        List<String> titles=new ArrayList<>();//*状态：0已取消，1已下单，2服务中，3已支付，4已完成，5待用户确认

/**
 *  UtilPreference.saveString(LoginActivity.this,"totalNum",totalNum);
 UtilPreference.saveString(LoginActivity.this,"cancelNum",cancelNum);
 UtilPreference.saveString(LoginActivity.this,"orderNum",orderNum);
 UtilPreference.saveString(LoginActivity.this,"serveNum",serveNum);
 UtilPreference.saveString(LoginActivity.this,"payNum",payNum);
 UtilPreference.saveString(LoginActivity.this,"completeNum",completeNum);
 UtilPreference.saveString(LoginActivity.this,"toConfirmedNum",toConfirmedNum);
 */
        titles.add("全部 "+UtilPreference.getStringValue(getActivity(),"totalNum"));
        titles.add("已下单 "+UtilPreference.getStringValue(getActivity(),"orderNum"));
        titles.add("待确认 "+UtilPreference.getStringValue(getActivity(),"toConfirmedNum"));


        titles.add("服务中 "+UtilPreference.getStringValue(getActivity(),"serveNum"));
        titles.add("已支付 "+UtilPreference.getStringValue(getActivity(),"payNum"));


        titles.add("已完成 "+UtilPreference.getStringValue(getActivity(),"completeNum"));
        titles.add("已取消 "+UtilPreference.getStringValue(getActivity(),"cancelNum"));

        for (int i = 0; i < titles.size(); i++) {
            tabs.addTab(tabs.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments=new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            fragments.add(ListsFragment.getInstance(titles.get(i)));
        }
        FragmentAdapter fragmentPagerAdapter=new FragmentAdapter(getFragmentManager(),fragments,titles);
        //给viewpager设置适配器
        viewPager.setAdapter(fragmentPagerAdapter);
        //将viewpager和Tablayout关联起来
        tabs.setupWithViewPager(viewPager);
        tabs.setTabTextColors(Color.BLACK,Color.RED);
        //给tabLayout设置适配器
        tabs.setTabsFromPagerAdapter(fragmentPagerAdapter);

    }

    private void initRefresh() {
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.RED);
        //设置下拉刷新监听器
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "refresh", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //加载新数据
                        //adapter.notifyDataSetChanged();
                        //加载完调用该方法
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });

        //设置上拉监听器
        swipeRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {

                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //上拉加载数据
                       /* datas.add(System.currentTimeMillis()+"");
                        adapter.notifyDataSetChanged();*/
                        swipeRefreshLayout.setLoading(false);

                    }
                },1500);

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "-----------onResume:--------- ");
        initDatas() ;

    }


    private void initDatas() {

        String jkey = UtilPreference.getStringValue(getActivity(), "jkey");
        String captain_id = UtilPreference.getStringValue(getActivity(), "captain_id");
        service.getOrderNumber(jkey,captain_id).subscribeOn(Schedulers.io()).
                subscribe(new Subscriber<Result11<OrderNumber>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result11<OrderNumber> orderNumberResult11) {
                        OrderNumber orderNumber = orderNumberResult11.getMessage();

                        Log.i(TAG, "---onNext: --"+orderNumber.toString());
                        totalNum = orderNumber.getAll_num();
                        cancelNum = orderNumber.getS0_num();
                        orderNum = orderNumber.getS1_num();
                        serveNum = orderNumber.getS2_num();
                        payNum = orderNumber.getS3_num();
                        completeNum = orderNumber.getS4_num();
                        toConfirmedNum=orderNumber.getS5_num();

                        UtilPreference.saveString(getActivity(),"totalNum",totalNum);
                        UtilPreference.saveString(getActivity(),"cancelNum",cancelNum);
                        UtilPreference.saveString(getActivity(),"orderNum",orderNum);
                        UtilPreference.saveString(getActivity(),"serveNum",serveNum);
                        UtilPreference.saveString(getActivity(),"payNum",payNum);
                        UtilPreference.saveString(getActivity(),"completeNum",completeNum);
                        UtilPreference.saveString(getActivity(),"toConfirmedNum",toConfirmedNum);

                        Log.i(TAG, "---mainfragmenttotalNum:"+totalNum);
                        Log.i(TAG, "----mainfragmentt:totalNum--"+totalNum+"---cancelNum--"+cancelNum+"---orderNum--"+orderNum+"---serveNum--"+serveNum+"---payNum--"+payNum+"---completeNum--"+completeNum);

                    }
                });


    }


}
