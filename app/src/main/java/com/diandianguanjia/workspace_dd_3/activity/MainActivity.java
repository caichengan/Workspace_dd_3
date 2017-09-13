package com.diandianguanjia.workspace_dd_3.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.base.BaseApi;
import com.diandianguanjia.workspace_dd_3.fragment.MainFragment;
import com.diandianguanjia.workspace_dd_3.fragment.PersonalFragment;
import com.diandianguanjia.workspace_dd_3.fragment.StaticticsFragment;
import com.diandianguanjia.workspace_dd_3.mode.NotJkey;
import com.diandianguanjia.workspace_dd_3.mode.OrderNumber;
import com.diandianguanjia.workspace_dd_3.mode.Result;
import com.diandianguanjia.workspace_dd_3.mode.Result11;
import com.diandianguanjia.workspace_dd_3.mode.StatisticsNumber;
import com.diandianguanjia.workspace_dd_3.net.ApiService;
import com.diandianguanjia.workspace_dd_3.service.MessagePushService;
import com.diandianguanjia.workspace_dd_3.service.NotificatioinService;
import com.diandianguanjia.workspace_dd_3.utils.IntentUtils;
import com.diandianguanjia.workspace_dd_3.utils.MyActivityManager;
import com.diandianguanjia.workspace_dd_3.utils.Preferences;
import com.diandianguanjia.workspace_dd_3.utils.UtilPreference;
import com.igexin.sdk.PushManager;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.duduhuo.dialog.smartisan.NormalDialog;
import cc.duduhuo.dialog.smartisan.SmartisanDialog;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends FragmentActivity implements View.OnClickListener {




    @BindView(R.id.content)
    FrameLayout mContent;
    @BindView(R.id.rbHome)
    RadioButton mRbHome;
    @BindView(R.id.rbIndent)
    RadioButton mRbIndent;

    @BindView(R.id.rbPerson)
    RadioButton mRbPerson;
    @BindView(R.id.rgTools)
    RadioGroup mRgTools;

    private Fragment[] mFragments;
    private int mIndex;
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    private ApiService service;
    private String toJSONString;

    private String totalNum;
    private String orderNum;
    private String serveNum;
    private String payNum;
    private String completeNum;
    private String toConfirmedNum;
    private String cancelNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), NotificatioinService.class);


        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), MessagePushService.class);
        MyActivityManager.getInstance().addActivity(this);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://weixin.dd1760.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(ApiService.class);




        initView();




        boolean is_login = UtilPreference.getBooleanValue(this, "is_login");


        if (is_login){
            String jkey = UtilPreference.getStringValue(this, "jkey");
            String captain_id = UtilPreference.getStringValue(this, "captain_id");
            Logger.d(jkey+is_login+captain_id);

            Logger.d(jkey+"--captain_id--"+captain_id+"---clientid--"+ Preferences.getString("clientid",""));

            int parseInt = Integer.parseInt(captain_id);

            service.decideKey(jkey,parseInt).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result<NotJkey>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Result<NotJkey> notJkeyResult) {

                           Logger.d("-----onNext: "+notJkeyResult.getCode());
                            int code = notJkeyResult.getCode();


                            newDialogNot(code);

                        }
                    });



            initFragment();
        }else{
            IntentUtils.startActivityFinish(this,LoginActivity.class);
            finish();
        }
    }
    private void newDialogNot(int code) {
        if (code==0){
            final NormalDialog dialog = SmartisanDialog.createNormalDialog(this);
            dialog.setTitle("警告");
            dialog.setMsg("你的账户已在其他手机登录，请重新登录！");
            dialog.setLeftBtnText("取消");
            dialog.setRightBtnText("确定");
            dialog.setCancelable(false);
            dialog.setOnSelectListener(new NormalDialog.OnSelectListener() {
                @Override
                public void onLeftSelect() {
                    dialog.dismiss();
                }

                @Override
                public void onRightSelect() {

                    dialog.dismiss();
                    IntentUtils.startActivityFinish(MainActivity.this,LoginActivity.class);
                }
            });
            dialog.show();

        }


    }





    private void initFragment() {
        // 首页
        MainFragment homeFragment =  MainFragment.getInstance();
        StaticticsFragment staticticsFragment =  StaticticsFragment.newInstance();
        PersonalFragment personalFragment =  PersonalFragment.newInstance();


        // 添加到数组
        mFragments = new Fragment[]{homeFragment,staticticsFragment, personalFragment};

        // 开启事务

        ft = getSupportFragmentManager().beginTransaction();

        // 添加首页
        ft.add(R.id.content, homeFragment).commit();

        // 默认设置为第0个
        setIndexSelected(0);
    }

    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // 隐藏
        ft.hide(mFragments[mIndex]);
        // 判断是否添加
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.content, mFragments[index]).show(mFragments[index]);
        } else {
            ft.show(mFragments[index]);
        }

        ft.commit();
        // 再次赋值
        mIndex = index;
    }








    private static final String TAG = "MainActivity";

    private void initView() {
        mRbHome.setOnClickListener(this);
        mRbIndent.setOnClickListener(this);
        mRbPerson.setOnClickListener(this);



        //加载数据






    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;

                    return true;
                } else {
                    MyActivityManager.getInstance().moveTaskToBack(this);// 不退出，后台运行
                }



                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.rbHome:
                setIndexSelected(0);
                break;
            case R.id.rbIndent:
                // setIndexSelected(1);
                setIndexSelected(1);
                break;

            case R.id.rbPerson:
                setIndexSelected(2);
                break;
        }
    }
}
