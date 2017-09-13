package com.diandianguanjia.workspace_dd_3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.utils.IntentUtils;
import com.diandianguanjia.workspace_dd_3.utils.MyActivityManager;

import butterknife.ButterKnife;


public class GuidingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duiding);

       ImageView btnImg= (ImageView) findViewById(R.id.btn);

        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);

/** 设置旋转动画 */
        final ScaleAnimation animation =new ScaleAnimation(0f,1f,0f,1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);//设置动画持续时间
/** 常用方法 */
//animation.setRepeatCount(int repeatCount);//设置重复次数
//animation.setFillAfter(boolean);//动画执行完后是否停留在执行完的状态
//animation.setStartOffset(long startOffset);//执行前的等待时间

        btnImg.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                IntentUtils.startActivityFinish(GuidingActivity.this,MainActivity.class);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }
}
