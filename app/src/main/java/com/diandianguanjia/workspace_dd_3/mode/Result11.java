package com.diandianguanjia.workspace_dd_3.mode;

/**
 * Created by an on 2017/8/17.
 */

public class Result11<T> {

    private int code;
    private T message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
