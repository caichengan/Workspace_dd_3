package com.diandianguanjia.workspace_dd_3.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.base.BaseActivity;
import com.diandianguanjia.workspace_dd_3.base.BaseApi;
import com.diandianguanjia.workspace_dd_3.mode.OrderNumber;
import com.diandianguanjia.workspace_dd_3.mode.Result;
import com.diandianguanjia.workspace_dd_3.mode.Result11;
import com.diandianguanjia.workspace_dd_3.mode.StatisticsNumber;
import com.diandianguanjia.workspace_dd_3.mode.UserInfo;
import com.diandianguanjia.workspace_dd_3.net.ApiService;
import com.diandianguanjia.workspace_dd_3.utils.DateUtil;
import com.diandianguanjia.workspace_dd_3.utils.IntentUtils;
import com.diandianguanjia.workspace_dd_3.utils.MyActivityManager;
import com.diandianguanjia.workspace_dd_3.utils.Preferences;
import com.diandianguanjia.workspace_dd_3.utils.StringUtil;
import com.diandianguanjia.workspace_dd_3.utils.TimeUtil;
import com.diandianguanjia.workspace_dd_3.utils.ToastUitl;
import com.diandianguanjia.workspace_dd_3.utils.UtilPreference;


import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginActivity extends BaseActivity implements OnClickListener {


    @BindView(R.id.ddhk_login_login)
    Button btLogin;
    @BindView(R.id.ddhk_login_forgetpwd)
    Button btForgetpwd;


    @BindView(R.id.ddhk_login_register)
    LinearLayout llRegsiter;
    @BindView(R.id.ddhk_login_username)
    EditText edUserName;
    @BindView(R.id.ddhk_login_password)
    EditText edPassword;
    private String username;
    private String password;
    private Handler handler;
    private String totalNum;
    private String orderNum;
    private String serveNum;
    private String payNum;
    private String completeNum;
    private String toConfirmedNum;
    private String cancelNum;

    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);



        MyActivityManager.getInstance().addActivity(this);

        initView();






    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }



    private void initView() {
        // TODO Auto-generated method stub
        btLogin.setOnClickListener(this);
        btForgetpwd.setOnClickListener(this);
        llRegsiter.setOnClickListener(this);

    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.ddhk_login_login:
                doLogin();

                break;
            case R.id.ddhk_login_forgetpwd:

                break;
            case R.id.ddhk_login_register:

                break;

            default:
                break;
        }
    }



    private void doLogin() {
        username = edUserName.getText().toString();
        password = edPassword.getText().toString();

        if (StringUtil.isBlank(username)) {
            edUserName.setError("用户名不能为空！");
            edUserName.requestFocus();
            return;
        }
        if (StringUtil.isBlank(password)) {
            edPassword.setError("密码不能为空");
            edPassword.requestFocus();
            return;
        }


        String clientid = Preferences.getString("clientid","",LoginActivity.this);

        Log.i(TAG, "-----doLogin: ---"+clientid);

        if (clientid==null || clientid=="" ||clientid=="NULL" ||clientid=="null"){
            return;
        }
        showProgress("Loading...");
        BaseApi.getApiService().getLoginUsername(username,password,clientid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Subscriber<UserInfo>() {
                   @Override
                   public void onCompleted() {






                   }
                   @Override
                   public void onError(Throwable e) {
                       disShowProgress();
                       ToastUitl.showShort("账户或密码错误，请重试...");

                   }
                   @Override
                   public void onNext(UserInfo userInfo) {

                       UtilPreference.saveString(LoginActivity.this,"captain_id",userInfo.getMessage().getCaptain_id());
                       UtilPreference.saveString(LoginActivity.this,"jkey",userInfo.getMessage().getJkey());
                       UtilPreference.saveString(LoginActivity.this,"username",userInfo.getMessage().getUsername());
                       UtilPreference.saveString(LoginActivity.this,"head_img",userInfo.getMessage().getHead_img());
                       UtilPreference.saveBoolean(LoginActivity.this,"is_login",true);


                       initDatas(userInfo.getMessage().getJkey(),userInfo.getMessage().getCaptain_id());
                   }
               });


    }

    private void initDatas(final String jkey, final String captain_id) {

        BaseApi.getApiService().getOrderNumber(jkey,captain_id).subscribeOn(Schedulers.io()).
                subscribe(new Subscriber<Result11<OrderNumber>>() {
                    @Override
                    public void onCompleted() {

                        ToastUitl.showShort("登录成功");
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

                        UtilPreference.saveString(LoginActivity.this,"totalNum",totalNum);
                        UtilPreference.saveString(LoginActivity.this,"cancelNum",cancelNum);
                        UtilPreference.saveString(LoginActivity.this,"orderNum",orderNum);
                        UtilPreference.saveString(LoginActivity.this,"serveNum",serveNum);
                        UtilPreference.saveString(LoginActivity.this,"payNum",payNum);
                        UtilPreference.saveString(LoginActivity.this,"completeNum",completeNum);
                        UtilPreference.saveString(LoginActivity.this,"toConfirmedNum",toConfirmedNum);

                        Log.i(TAG, "---onNext: totalNum:"+totalNum);
                        Log.i(TAG, "----onCreate:totalNum--"+totalNum+"---cancelNum--"+cancelNum+"---orderNum--"+orderNum+"---serveNum--"+serveNum+"---payNum--"+payNum+"---completeNum--"+completeNum);


                        initStaticNum(jkey,captain_id);
                    }
                });



    }

    private void initStaticNum(String jkey, String captain_id) {


        BaseApi.getApiService().getStatNumber(jkey,captain_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result11<StatisticsNumber>>() {
                    @Override
                    public void onCompleted() {

                        disShowProgress();
                        IntentUtils.startActivityFinish(LoginActivity.this,MainActivity.class);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        disShowProgress();
                    }

                    @Override
                    public void onNext(Result11<StatisticsNumber> statisticsNumberResult) {
                        StatisticsNumber statisticsNumber = statisticsNumberResult.getMessage();

                        Log.i(TAG, "---initStaticNum: --"+statisticsNumber.toString());

                        String dauNum=statisticsNumber.getDay();
                        String weekNum = statisticsNumber.getWeek();
                        String monthNum = statisticsNumber.getMonth();
                        String quarterNum = statisticsNumber.getSeason();
                        String yearNum = statisticsNumber.getYear();

                        UtilPreference.saveString(LoginActivity.this,"dauNum",dauNum);
                        UtilPreference.saveString(LoginActivity.this,"weekNum",weekNum);
                        UtilPreference.saveString(LoginActivity.this,"monthNum",monthNum);
                        UtilPreference.saveString(LoginActivity.this,"quarterNum",quarterNum);
                        UtilPreference.saveString(LoginActivity.this,"yearNum",yearNum);


                        Log.i(TAG, "----initStaticNum------e:dauNum--"+dauNum+"---weekNum--"+weekNum+"---monthNum--"+monthNum+"--quarterNum"+quarterNum+"--"+yearNum);


                    }
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (null != locationClient) {
            // 一定要执行AMapLocationClient的onDestroy释放资源
            locationClient.stopLocation();
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }*/

    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(LoginActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    MyActivityManager.getInstance().moveTaskToBack(this);// 不退出，后台运行
                }




                break;
        }

        UtilPreference.clearLocalValues(this);
        return super.onKeyUp(keyCode, event);
    }

}
