package com.diandianguanjia.workspace_dd_3.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.diandianguanjia.workspace_dd_3.utils.MyActivityManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by RaphetS on 2016/10/15.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected final static int DATA_LOAD_ING = 0x001;
    protected final static int DATA_LOAD_COMPLETE = 0x002;
    protected final static int DATA_LOAD_FAIL = 0x003;

    public static final Handler handler = new Handler();
    /**
     * 加载等待效果
     */
    public ProgressDialog progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);


    }

    public abstract int getLayoutId();


    /**
     * Activity销毁，关闭加载效果
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progress != null) {
            progress.dismiss();
        }
    }

    /**
     * Activity暂停，关闭加载效果
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (progress != null) {
            progress.dismiss();
        }
    }

    /**
     * Activity停止，关闭加载效果
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (progress != null) {
            progress.dismiss();
        }
    }



    /**
     * 触发手机返回按钮
     */
    @Override
    public void onBackPressed() {
        MyActivityManager.getInstance().removeActivity(this);
    }


    protected void setToolbar(Toolbar toolbar, String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                }else {
                    finish();
                }
            }
        });
    }

    /**
     * 滚动条超时
     *
     * @author mo
     *
     */
    @SuppressWarnings("unused")
    private class ProgressTimeOut extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(30000);
                Message message1 = new Message();
              disProgressHander.sendMessage(message1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 消息队列方式显示进度
     */
    @SuppressLint("HandlerLeak")
    public Handler progressHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (progress != null) {
                progress.dismiss();
            }
            progress = new ProgressDialog(BaseActivity.this);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setMessage(msg.obj.toString());
            progress.setCanceledOnTouchOutside(false);
            progress.setCancelable(true);
            progress.show();
        }
    };

    /**
     * 隐藏消息队列进度的显示
     */
    public Handler disProgressHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (progress != null) {
                progress.dismiss();
            }
        }
    };

    /**
     * 显示字符串消息
     *
     * @param message
     */
    public void showProgress(String message) {
        if (progress != null) {
            progress.dismiss();
        }
        progress = new ProgressDialog(BaseActivity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(message);
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(true);
        progress.show();
    }

    /**
     * 隐藏字符串消息
     */
    public void disShowProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    /**
     * 提示信息
     *
     */
    public void showErrorMsg(String message) {
        final String str = message;
        BaseActivity.handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), str,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    /**
     * 提示信息号或请求失败信息
     *
     * showErrorRequestFair
     *
     */
    public void showE404() {
        BaseActivity.handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "手机信号差或服务器维护，请稍候重试。谢谢！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    /**
     * 提示信息
     *
     */
    public void showMsgAndDisProgress(String message) {
        final String str = message;
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), str,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                disShowProgress();
            }
        });
    }

}
