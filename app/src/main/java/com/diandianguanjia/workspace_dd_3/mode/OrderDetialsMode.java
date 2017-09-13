package com.diandianguanjia.workspace_dd_3.mode;

/**
 * Created by an on 2017/9/12.
 */

public class OrderDetialsMode {


    /**
     * code : 1
     * message : {"c_time":"2017-09-09 11:21:10","user_name":"刘小","user_phone":"18029969916","province":"广东省","city":"中山市","district":"板芙镇","address":"板芙7队","serv_desc":"kong","need_serv":"门坏","serv":"维修维护-卫浴洁具-卫浴、洁具维修"}
     */

    private int code;
    private MessageBean message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * c_time : 2017-09-09 11:21:10
         * user_name : 刘小
         * user_phone : 18029969916
         * province : 广东省
         * city : 中山市
         * district : 板芙镇
         * address : 板芙7队
         * serv_desc : kong
         * need_serv : 门坏
         * serv : 维修维护-卫浴洁具-卫浴、洁具维修
         */

        private String c_time;
        private String user_name;
        private String user_phone;
        private String province;
        private String city;
        private String district;
        private String address;
        private String serv_desc;
        private String need_serv;
        private String serv;

        public String getC_time() {
            return c_time;
        }

        public void setC_time(String c_time) {
            this.c_time = c_time;
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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getServ_desc() {
            return serv_desc;
        }

        public void setServ_desc(String serv_desc) {
            this.serv_desc = serv_desc;
        }

        public String getNeed_serv() {
            return need_serv;
        }

        public void setNeed_serv(String need_serv) {
            this.need_serv = need_serv;
        }

        public String getServ() {
            return serv;
        }

        public void setServ(String serv) {
            this.serv = serv;
        }
    }
}
