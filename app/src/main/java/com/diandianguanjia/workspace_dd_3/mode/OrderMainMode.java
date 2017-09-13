package com.diandianguanjia.workspace_dd_3.mode;

import java.util.List;

/**
 * Created by an on 2017/8/28.
 */

public class OrderMainMode {


    /**
     * code : 1
     * message : [{"id":"1075","serv1_id":"2","user_name":"安190","user_phone":"13531829360","c_time":"2017-09-07 15:20:19","sum":"预计中...","status":"0","serv_name":"家政服务"},{"id":"1073","serv1_id":"1","user_name":"安190","user_phone":"13531829360","c_time":"2017-09-07 14:50:08","sum":"预计中...","status":"0","serv_name":"维修维护"},{"id":"1072","serv1_id":"2","user_name":"安190","user_phone":"13531829360","c_time":"2017-09-07 14:49:15","sum":"预计中...","status":"0","serv_name":"家政服务"},{"id":"1071","serv1_id":"2","user_name":"安190","user_phone":"13531829360","c_time":"2017-09-07 14:42:19","sum":"0.10","status":"0","serv_name":"家政服务"},{"id":"1070","serv1_id":"2","user_name":"安190","user_phone":"13531829360","c_time":"2017-09-07 14:33:30","sum":"0.01","status":"0","serv_name":"家政服务"},{"id":"1068","serv1_id":"1","user_name":"安190","user_phone":"13531829360","c_time":"2017-09-07 14:23:53","sum":"0.10","status":"0","serv_name":"维修维护"},{"id":"1067","serv1_id":"2","user_name":"安190","user_phone":"13531829360","c_time":"2017-09-07 14:23:28","sum":"预计中...","status":"0","serv_name":"家政服务"},{"id":"1063","serv1_id":"1","user_name":"安190","user_phone":"13531829360","c_time":"2017-09-07 12:01:36","sum":"预计中...","status":"0","serv_name":"维修维护"},{"id":"1062","serv1_id":"2","user_name":"安190","user_phone":"13531829360","c_time":"2017-09-07 12:01:08","sum":"预计中...","status":"0","serv_name":"家政服务"},{"id":"1061","serv1_id":"2","user_name":"安190","user_phone":"13531829360","c_time":"2017-09-07 12:00:18","sum":"预计中...","status":"0","serv_name":"家政服务"}]
     */

    private int code;
    private List<MessageBean> message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * id : 1075
         * serv1_id : 2
         * user_name : 安190
         * user_phone : 13531829360
         * c_time : 2017-09-07 15:20:19
         * sum : 预计中...
         * status : 0
         * serv_name : 家政服务
         */

        private String id;
        private String serv1_id;
        private String user_name;
        private String user_phone;
        private String c_time;
        private String sum;
        private String status;
        private String serv_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getServ1_id() {
            return serv1_id;
        }

        public void setServ1_id(String serv1_id) {
            this.serv1_id = serv1_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getC_time() {
            return c_time;
        }

        public void setC_time(String c_time) {
            this.c_time = c_time;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getServ_name() {
            return serv_name;
        }

        public void setServ_name(String serv_name) {
            this.serv_name = serv_name;
        }
    }
}
