package com.diandianguanjia.workspace_dd_3.mode;

import java.util.List;

/**
 * Created by an on 2017/9/8.
 */

public class MessageBean {


    /**
     * data : [{"id":"1066","serv1_id":"1","user_name":"郭建明","user_phone":"18933445803","c_time":"2017-09-07 14:16:12","sum":"11.00","status":"4"},{"id":"1065","serv1_id":"1","user_name":"揭英聪","user_phone":"13425599368","c_time":"2017-09-07 13:36:53","sum":"0.01","status":"4"},{"id":"983","serv1_id":"1","user_name":"揭英聪","user_phone":"13425599368","c_time":"2017-09-06 14:11:11","sum":"0.01","status":"4"},{"id":"981","serv1_id":"1","user_name":"揭英聪","user_phone":"13425599368","c_time":"2017-09-06 14:10:45","sum":"0.01","status":"4"}]
     * sum : 11.03
     */

    private double sum;
    private List<DataBean> data;

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1066
         * serv1_id : 1
         * user_name : 郭建明
         * user_phone : 18933445803
         * c_time : 2017-09-07 14:16:12
         * sum : 11.00
         * status : 4
         */

        private String id;
        private String serv1_id;
        private String user_name;
        private String user_phone;
        private String c_time;
        private String sum;
        private String status;

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
    }
}

