package com.diandianguanjia.workspace_dd_3.mode;

import java.io.Serializable;


public class AroundServerBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String type;
    private String photo_url;
    private String boss_name;
    private String phone;
    private String wu_score;
    private String wu_score_num;
    private String join_time;
    private String province;
    private String city;
    private String district;
    private String address;
    private String lng;
    private String lat;
    private String company_name;
    private String distance;
    private String dd_name;
    private String photo_height;
    private String photo_width;
    private String user_province;
    private String user_city;
    private String user_district;
    private String user_address;
    private boolean is_bind;

    public String getUser_province() {
        return user_province;
    }

    public void setUser_province(String user_province) {
        this.user_province = user_province;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getUser_district() {
        return user_district;
    }

    public void setUser_district(String user_district) {
        this.user_district = user_district;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    @Override
    public String toString() {
        return "AroundServerBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", boss_name='" + boss_name + '\'' +
                ", phone='" + phone + '\'' +
                ", wu_score='" + wu_score + '\'' +
                ", wu_score_num='" + wu_score_num + '\'' +
                ", join_time='" + join_time + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", company_name='" + company_name + '\'' +
                ", distance='" + distance + '\'' +
                ", dd_name='" + dd_name + '\'' +
                ", photo_height='" + photo_height + '\'' +
                ", photo_width='" + photo_width + '\'' +
                ", user_province='" + user_province + '\'' +
                ", user_city='" + user_city + '\'' +
                ", user_district='" + user_district + '\'' +
                ", user_address='" + user_address + '\'' +
                ", is_bind=" + is_bind +
                '}';
    }

    public boolean is_bind() {
        return is_bind;
    }

    public void setIs_bind(boolean is_bind) {
        this.is_bind = is_bind;
    }

    public String getPhoto_height() {
        return photo_height;
    }

    public void setPhoto_height(String photo_height) {
        this.photo_height = photo_height;
    }

    public String getPhoto_width() {
        return photo_width;
    }

    public void setPhoto_width(String photo_width) {
        this.photo_width = photo_width;
    }

    public String getDd_name() {
        return dd_name;
    }

    public void setDd_name(String dd_name) {
        this.dd_name = dd_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getBoss_name() {
        return boss_name;
    }

    public void setBoss_name(String boss_name) {
        this.boss_name = boss_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWu_score() {
        return wu_score;
    }

    public void setWu_score(String wu_score) {
        this.wu_score = wu_score;
    }

    public String getWu_score_num() {
        return wu_score_num;
    }

    public void setWu_score_num(String wu_score_num) {
        this.wu_score_num = wu_score_num;
    }

    public String getJoin_time() {
        return join_time;
    }

    public void setJoin_time(String join_time) {
        this.join_time = join_time;
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

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    //

}
