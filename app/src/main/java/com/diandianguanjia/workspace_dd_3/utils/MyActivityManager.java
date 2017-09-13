package com.diandianguanjia.workspace_dd_3.utils;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于管理Activity的类。在程序退出的时候清除所有的Activity
 * 
 * @author mo
 * 
 */
public class MyActivityManager {

	private List<Activity> activities = null;
	private static MyActivityManager instance;

	private MyActivityManager() {
		activities = new ArrayList<Activity>();
	}

	public static MyActivityManager getInstance() {
		if (instance == null) {
			instance = new MyActivityManager();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		if (activities != null && activities.size() > 0) {
			if (!activities.contains(activity)) {
				activities.add(activity);
			}
		} else {
			activities.add(activity);
		}
	}

	/**
	 * 移除Activity到容器中
	 * 
	 * @param activity
	 */
	public void removeActivity(Activity activity) {
		activities.remove(activity);
		activity.finish();
	}

	/**
	 * 返回主界面
	 */
	public void backToMain() {
		if (activities != null && activities.size() > 0) {
			for (int i = 0; i < activities.size(); i++) {
				activities.get(i).finish();
			}
		}
	}

	/**
	 * 注销系统
	 * 
	 * @param context
	 */
	public void logout(final Context context) {
		// doLogout(context);
		// // 注销
		// for (Activity activity : activities) {
		// activity.finish();
		// }
		// activities.clear();
		// MainConstant.getInstance(context).logout();// 退出即时聊天服务器
		// // 获取音乐服务
		// MusicPlayService mService =
		// MusicPlayServiceProxy.getInstance(context)
		// .getmService();
		// if (mService != null) {
		// // 暂停音乐播放
		// mService.pause();
		// }
		//
		// Intent intent = new Intent(context, ActivityLogin.class);
		// context.startActivity(intent);
	}

	/**
	 * 退出应用
	 */
	public void exit(Context mContext) {
		doLogout(mContext);
		//MainConstant.getInstance(mContext).logout();// 退出即时聊天服务器
		try {
			for (Activity activity : activities) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	/**
	 * 后台运行，不退出
	 * 
	 * @param context
	 */
	public void moveTaskToBack(Context context) {
		try {
			for (Activity activity : activities) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			((Activity) context).moveTaskToBack(false);
		}
	}

	/**
	 * 注销或退出客户端时注销用户在服务端的登录
	 */
	private void doLogout(Context mContext) {
		// String url = ConfigApp.HC_LOGOUT;
		// String userId = UtilPreference.getStringValue(mContext, "userId");
		// RequestParams params = new RequestParams();
		// params.add("userId", userId);
		//
		// HttpUtil.get(url, params, new RequestListener() {
		//
		// @Override
		// public void success(String response) {
		// Log.info("MyActivityManager", "注销成功：" + response);
		// }
		//
		// @Override
		// public void failed(Throwable error) {
		// Log.info("MyActivityManager", "注销失败：" + error.getMessage());
		// }
		// });

	}

}
