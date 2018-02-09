package com.one.zyk.soup.utils;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;


public class DownloadUtils {
    /***
     * 调用系统DownloadManager 下载文件
     */
    public static void download(String url, String type, Context context) {
        try {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(url);
            Request request = new Request(uri);
            // 设置允许使用的网络类型，这里是移动网络和wifi都可以
            request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
            // 禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
            // request.setShowRunningNotification(false);
            // 显示下载界面
            request.setVisibleInDownloadsUi(true);
            // 开始下载同时得到该下载文件对应的下载ID并保存
            long id = downloadManager.enqueue(request);
            // 保存下载ID
            SharedPreferences preferences = context.getSharedPreferences("download", Context.MODE_PRIVATE);
            Editor editor = preferences.edit();
            editor.putLong(type, id);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
