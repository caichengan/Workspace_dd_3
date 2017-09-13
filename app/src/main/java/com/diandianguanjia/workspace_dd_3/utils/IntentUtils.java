package com.diandianguanjia.workspace_dd_3.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;

/**
 * @描述 intent常用工具类
 * @项目名称 AppClient
 * @包名 com.andorid.main.basic.aboutActivity
 * @类名 IntentUtils
 * @author chenlin
 * @date 2012年6月25日 上午8:08:55
 * @version 1.0
 */
public class IntentUtils {

    public static final int REQUEST_CODE_GALLERY = 0x11;
    public static final int REQUEST_CODE_CAMERA = 0x12;
    public final static int REQUEST_CODE_CROP = 0x13;
// 使用弱引用，避免不恰当地持有Activity或Fragment的引用。
// 持有Activity的引用会阻止Activity的内存回收，增大OOM的风险。

private Toast toast;


/**
* 跳转Activity
* @param mContxt
* @param clazz
*/
public  static void startActivity(Context mContxt,Class<? extends Activity> clazz) {

Intent intent = new Intent(mContxt, clazz);
mContxt.startActivity(intent);
}


/**
* 跳转Activity并结束前Activity
* @param mContxt
* @param clazz
*/
public  static void startActivityFinish(Activity mContxt,Class<? extends Activity> clazz) {

Intent intent = new Intent(mContxt, clazz);
mContxt.startActivity(intent);
mContxt.finish();
}


/**
* 跳转Activity并传递数据过去
* @param mContxt
* @param clazz
*/
public  static void startActivityBundle(Activity mContxt, Class<? extends Activity> clazz, Bundle bundle) {

Intent intent = new Intent(mContxt, clazz);
intent.putExtra("bundle",bundle);
mContxt.startActivity(intent);

}

/**
* 跳转Activity并传递数据过去结束前界面
* @param mContxt
* @param clazz
*/
public  static void startActivityBundleFinish(Activity mContxt, Class<? extends Activity> clazz, Bundle bundle) {

Intent intent = new Intent(mContxt, clazz);
intent.putExtra("bundle",bundle);
mContxt.startActivity(intent);
mContxt.finish();
}


/**
* 跳转Activity并传递数据到前界面
* @param mContxt
* @param clazz
*/
public  static void startActivityForResult(Activity mContxt,int requestCode, Class<? extends Activity> clazz) {

Intent intent = new Intent(mContxt, clazz);
mContxt.startActivityForResult(intent,requestCode);
}


    /**
     * 打电话
     *
     * @param context
     * @param phoneNum
     */
    public static void call(Context context, int phoneNum) {
        Intent intent = new Intent();
        // 启动电话程序
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel://" + phoneNum));
        context.startActivity(intent);
    }

    /**
     * 打开浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * 打开图片
     *
     * @param context
     * @param
     */
    public static void openImage(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        // intent.setDataAndType(Uri.parse("file:///mnt/sdcard/file/1.jgp"),
        // "image/*");
        intent.setDataAndType(Uri.parse("file:///" + path), "image/*");
        context.startActivity(intent);
    }

    /**
     * 打开音频
     *
     * @param context
     * @param
     */
    public static void openAudio(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file:///" + path), "audio/*");
        context.startActivity(intent);
    }

    /**
     * 打开视频文件
     *
     * @param context
     * @param
     */
    public static void openVideo(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file:///" + path), "video/*");
        context.startActivity(intent);
    }

    /**
     * 打开系统摄像头录像,并保存为图片
     *
     * @param context
     * @param path
     */
    public static void openCamera(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse(Environment.getExternalStorageDirectory() + "/Videos/" + System.currentTimeMillis() + ".jpg"));
        context.startActivity(intent);
    }

    /**
     * 打开系统摄像头录像,并保存为视频
     *
     * @param context
     * @param
     */
    public static void openCamera(Context context) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse(Environment.getExternalStorageDirectory() + "/Videos/" + System.currentTimeMillis() + ".mp4"));
        context.startActivity(intent);
    }


    /**
     * 发送对像
     *
     * @param
     */
    public static <T extends Serializable> void sendData(Context context, T t) {
        Intent intent = new Intent(context, t.getClass());
        intent.putExtra(t.getClass().getSimpleName(), t);// 要传递对像，对像必须是经过序列化的
        context.startActivity(intent);
    }

    /**
     * 获取对像
     *
     * @param view
     */
    public static <T extends Serializable> T getData(Activity context, View view) {
        return (T) context.getIntent().getSerializableExtra("account");
    }

    /**
     * 安装文件s
     *
     * @param context
     * @param apkFile
     */
    public static void installApp(Context context, File apkFile) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 卸载
     *
     * @param context
     * @param apkFile
     */
    public static void unInstallApp(Context context, File apkFile) {
        Uri packageURI = Uri.parse("package:com.andorid.main");
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);
    }

    /**
     * 获得裁剪的图片从图片集合里
     * @param context
     * @param sdcardTempFile
     * @param crop
     */
    public static void getimageFromGallery(Activity context, File sdcardTempFile, int crop) {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("output", Uri.fromFile(sdcardTempFile));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框 intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", crop);// 输出图片大小
        intent.putExtra("outputY", crop);
        context.startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }
    /**
     * 获得裁剪的图片从Camera里
     * @param context
     * @param sdcardTempFile
     * @param crop
     */
    public static void getimageFromCamera(Activity context, File sdcardTempFile, int crop) {
        Uri uri = Uri.fromFile(sdcardTempFile);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("output", uri);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比�? intent.putExtra("aspectY",
        // 1);
        intent.putExtra("outputX", crop);// 输出图片大小
        intent.putExtra("outputY", crop);
        context.startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    /**
     * 获得裁剪的图片从摄像头
     * @param context
     * @param sdcardTempFile
     * @param
     */
    public static void getImageFromCamera(Activity context, File sdcardTempFile) {
        Uri uri = Uri.fromFile(sdcardTempFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", uri);
        context.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }


}
