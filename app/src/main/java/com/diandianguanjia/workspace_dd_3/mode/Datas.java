package com.diandianguanjia.workspace_dd_3.mode;

/**
 * Created by an on 2017/8/17.
 */

public class Datas {

    private String message;
    private int code;
    double mTotalMoney;

    public double getmTotalMoney() {
        return mTotalMoney;
    }

    public void setmTotalMoney(double mTotalMoney) {
        this.mTotalMoney = mTotalMoney;
    }

    public Datas(String message,double mTotalMoney) {
        this.message=message;
        this.mTotalMoney=mTotalMoney;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
