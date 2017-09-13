package com.diandianguanjia.workspace_dd_3.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.activity.BranchActivity;
import com.diandianguanjia.workspace_dd_3.activity.LoginActivity;
import com.diandianguanjia.workspace_dd_3.activity.MyMessageActivity;
import com.diandianguanjia.workspace_dd_3.base.MyBaseFragment;
import com.diandianguanjia.workspace_dd_3.utils.IntentUtils;
import com.diandianguanjia.workspace_dd_3.utils.UtilPreference;
import com.diandianguanjia.workspace_dd_3.view.CircleImageView;
import com.diandianguanjia.workspace_dd_3.view.RxBus;
import com.orhanobut.logger.Logger;

import cc.duduhuo.dialog.smartisan.SmartisanDialog;
import cc.duduhuo.dialog.smartisan.WarningDialog;

/**
 * Created by an on 2017/8/28.
 */

public class PersonalFragment extends MyBaseFragment implements View.OnClickListener {

    Button backBtn;
    TextView tvTitle;
    Button btnRight;
    TextView tvName;
    TextView tvDesc;
    RelativeLayout load;
    LinearLayout myMessage;
    LinearLayout myBranch;

    CircleImageView ivAvatar;
    LinearLayout mySetting;
    private RxBus rxBus;
    private boolean is_login;


    public static PersonalFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setContentView() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void lazyLoad() {


        initView();

        is_login = UtilPreference.getBooleanValue(getContext(), "is_login");

        String username = UtilPreference.getStringValue(getContext(), "username");
        String head_img = UtilPreference.getStringValue(getContext(), "head_img");
        String jkey = UtilPreference.getStringValue(getContext(), "jkey");
        Logger.d(is_login +username+jkey);

        Logger.d(is_login);

        if (is_login){
            tvName.setText(username);

            Logger.d(head_img);

            if (!TextUtils.isEmpty(head_img)) {

                Glide.with(getContext()).load(head_img).into(ivAvatar);
            }
        }





    }

    private void initView() {
        backBtn=findViewById(R.id.backBtn);
        tvTitle=findViewById(R.id.tv_header);
        btnRight=findViewById(R.id.bt_header_right);

        tvName=findViewById(R.id.tvName);
        ivAvatar=findViewById(R.id.ivAvatar);
        tvDesc=findViewById(R.id.tvDesc);

        load=findViewById(R.id.load);
        //lin
        myMessage=findViewById(R.id.my_message);
        myBranch=findViewById(R.id.my_branch);
        mySetting=findViewById(R.id.my_setting);

        load.setOnClickListener(this);
        myMessage.setOnClickListener(this);
        myBranch.setOnClickListener(this);
        mySetting.setOnClickListener(this);
        backBtn.setVisibility(View.INVISIBLE);
        tvTitle.setText("我的");

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.my_message://信息
                IntentUtils.startActivity(getActivity(),MyMessageActivity.class);

                break;
            case R.id.my_branch://网点

                IntentUtils.startActivity(getActivity(),BranchActivity.class);

                break;
            case R.id.my_setting://设置

                final WarningDialog warningDialog = SmartisanDialog.createWarningDialog(getContext());

                warningDialog.setConfirmText("确定");
                warningDialog.setTitle("确定要切换账户吗 ？");
                warningDialog.setOnConfirmListener(new WarningDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm() {

                        warningDialog.dismiss();
                        IntentUtils.startActivityFinish(getActivity(),LoginActivity.class);

                    }
                });
                warningDialog.show();
                break;
            case R.id.load:

                Logger.d(is_login);
                if (!is_login) {
                    IntentUtils.startActivityFinish(getActivity(), LoginActivity.class);
                }

                break;

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.i(TAG, "--------onAttach:------ ");
    }
}
