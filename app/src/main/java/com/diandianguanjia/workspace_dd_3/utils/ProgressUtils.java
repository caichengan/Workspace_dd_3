package com.diandianguanjia.workspace_dd_3.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by an on 2017/8/2.
 */

public class ProgressUtils {

    public static ProgressDialog mProgressDialog;

    /**
     * 创建mProgressDialog
     */
    public static  void createProgressDialogTitle(Context mContext, String title) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        mProgressDialog.setTitle(title);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    /**
     * 隐藏mProgressDialog
     */
    public static  void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }



}
