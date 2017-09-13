package com.diandianguanjia.workspace_dd_3.net;

import com.diandianguanjia.workspace_dd_3.mode.NotJkey;
import com.diandianguanjia.workspace_dd_3.mode.OrderDetialsMode;
import com.diandianguanjia.workspace_dd_3.mode.OrderMainMode;
import com.diandianguanjia.workspace_dd_3.mode.OrderStatMode;
import com.diandianguanjia.workspace_dd_3.mode.OrderNumber;
import com.diandianguanjia.workspace_dd_3.mode.Result;
import com.diandianguanjia.workspace_dd_3.mode.Result11;
import com.diandianguanjia.workspace_dd_3.mode.StatisticsNumber;
import com.diandianguanjia.workspace_dd_3.mode.UserInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by an on 2017/8/31.
 */

public interface ApiService {





    /**
     * 登录获取用户信息
     * @param username 用户名
     * @param password 密码
     * @param clientid 设备id
     * @return
     */
    @FormUrlEncoded
    @POST("DDHK/index.php/Sk/Login/login")
    Observable<UserInfo> getLoginUsername(@Field("username")String username,@Field("password")String password,@Field("cid")String clientid);


    /**
     * 判断该设备是否在线
     * @param jkey  用户签名
     * @param captain_id 设备id
     * @return
     */
    @FormUrlEncoded
    @POST("DDHK/Sk/Login/jkey_l_or_d")
    Observable<Result<NotJkey>> decideKey(@Field(("jkey"))String jkey, @Field(("captain_id"))int captain_id);


    /**
     * 获取各个订单状态的数量
     * @param jkey 用户签名
     * @param captain_id 订单状态
     * @return
     */
    @FormUrlEncoded
    @POST("DDHK/Sk/Order/get_all_order_num")
    Observable<Result11<OrderNumber>> getOrderNumber(@Field(("jkey"))String jkey, @Field(("captain_id"))String captain_id);

    /**
     * http://weixin.dd1760.com/DDHK/Sk/Order/get_all_order_num_by_time?jkey=UobdBuyPvGKqO8gm1a03lWfhYjpFwTD9&captain_id=122
     * 获取各个统计状态的数量
     * @param jkey 用户签名
     * @param captain_id id
     * @return
     */
    @FormUrlEncoded
    @POST("DDHK/Sk/Order/get_all_order_num_by_time")
    Observable<Result11<StatisticsNumber>> getStatNumber(@Field(("jkey"))String jkey, @Field(("captain_id"))String captain_id);



    //http://weixin.dd1760.com/DDHK/Sk/Order/get_all_order?jkey=UobdBuyPvGKqO8gm1a03lWfhYjpFwTD9&captain_id=122&subscript=10

    /**
     * 获取首页订单：0已取消，1已下单，2服务中，3已支付，4已完成，5待用户确认
     *
     * @param  jkey(调用接口凭证)
     * @param  captain_id(社区店id)
     *  @param  status(订单状态)：0已取消，1已下单，2服务中，3已支付，4已完成，5待用户确认
     * @param  subscript(从第几条订单开始返回，下标)(选填)
     * @return id,serv1_id,user_name,user_phone,c_time,sum
     * */
    @FormUrlEncoded
    @POST("DDHK/Sk/Order/get_all_order")
    Observable<OrderMainMode> getOrderDatas(@Field(("jkey"))String jkey, @Field(("captain_id"))String captain_id, @Field(("status"))String status, @Field(("subscript"))int subscript);

   /***
    * 获取全部订单
    *
     * @param  jkey(调用接口凭证)
     * @param  captain_id(社区店id)
     * @param  subscript(从第几条订单开始返回，下标)(选填)
     * @return id,serv1_id,user_name,user_phone,c_time,sum
     * */
    @FormUrlEncoded
    @POST("DDHK/Sk/Order/get_all_order")
    Observable<OrderMainMode> getOrderAllDatas(@Field(("jkey"))String jkey, @Field(("captain_id"))String captain_id, @Field(("subscript"))int subscript);

    /**
     * http://weixin.dd1760.com/DDHK/Sk/Order/get_all_order_by_time?jkey=UobdBuyPvGKqO8gm1a03lWfhYjpFwTD9&
     * captain_id=122&begin_time=2017-09-05%2008:09:43&end_time=2017-09-08%2008:09:43&status=4&subscript=0
     *
     *   获取统计模块已完成订单（每年、每季度、每月、每周、每天）
     *
     * @param  jkey(调用接口凭证)
     * @param  captain_id(社区店id)
     * @param  begin_time(查询开始时间)(选填)
     * @param  end_time(查询结束时间)(选填)
     * @param  status(订单状态)(选填)
     * @param  subscript(从第几条订单开始返回，下标)(选填)
     * @return id,serv1_id,user_name,user_phone,c_time,sum
     *
     */
    @FormUrlEncoded
    @POST("DDHK/Sk/Order/get_all_order_by_time")
    Observable<OrderStatMode> getOrderStaticDatas(@Field(("jkey"))String jkey, @Field(("captain_id"))String captain_id, @Field(("begin_time"))String begin_time, @Field(("end_time"))String end_time, @Field(("status"))String status, @Field(("subscript"))int subscript);



    /**
     * 获取订单详细信息
     * @param key
     * @param orderId  OrderDetialsMode订单详细信息的mode
     * @return
     */
    @GET("DDHK/Sk/Order/get_order_data_by_id")
    Observable<OrderDetialsMode> getOrderDetials(@Query("jkey") String key, @Query("captain_id") String captain_id,@Query("order_id") String orderId);


    /**
     * 获取该用户的4S点旗下的网点
     *
     * @param jkey
     * @param captain_id   OrderDetialsMode  旗下网点的mode
     * @return
     */
    @FormUrlEncoded
    @POST("")
    Observable<OrderDetialsMode> getUser4SBranch(@Field(("jkey"))String jkey, @Field(("captain_id"))String captain_id);



    /**
     * 确认价格
     * @param jkey  用户签名
     * @param captain_id 设备id
     * @return
     */
    @FormUrlEncoded
    @POST("DDHK/Sk/Login/jkey_l_or_d")
    Observable<Result<NotJkey>> postOrderSum(@Field(("jkey"))String jkey, @Field(("captain_id"))int captain_id,@Field(("orderId"))String orderId, @Field(("editSum"))String editSum,@Field(("editDesc"))String editDesc);



}
