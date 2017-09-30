package com.one.zyk.chickensoup.http;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

public class CompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            // 得到文件下载完成的路径
            Query query = new Query();
            query.setFilterById(id);
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Cursor cursor = downloadManager.query(query);
            cursor.moveToNext();
            if (cursor.getCount() <= 0) {
                return;
            }
            String path = cursor.getString(cursor.getColumnIndex("local_filename"));
            if (TextUtils.isEmpty(path)) {
                return;
            }
            long downloadID = context.getSharedPreferences("download", Context.MODE_PRIVATE).getLong("apk", 0);
            long patchID = context.getSharedPreferences("download", Context.MODE_PRIVATE).getLong("patch", 0);
            if (downloadID == id) {
                // 安装
                Intent intent_install = new Intent(Intent.ACTION_VIEW);
                intent_install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent_install.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
                context.startActivity(intent_install);
            }
            if (patchID == id) {
                SharedPreferences preferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("patch", path);
                editor.apply();

            }
        }
    }
}
