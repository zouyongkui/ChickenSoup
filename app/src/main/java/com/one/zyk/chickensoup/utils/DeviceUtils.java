package com.one.zyk.chickensoup.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.one.zyk.chickensoup.SoupApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * Created by Administrator
 * Date:2017/5/25
 * Time:17:46
 * Author:BruceChang
 * Function:
 */

public class DeviceUtils {

    /**
     * 获取设备IMEI码
     * @return
     */
    public static String getIMEI() {
        String id = "";
        if (ActivityCompat.checkSelfPermission(SoupApp.context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) SoupApp.context.getSystemService(Context.TELEPHONY_SERVICE);
            id = tm.getDeviceId();
            if (!TextUtils.isEmpty(id)) {
                Log.e("trueimei", id);
                return id;
            }
        }
        File installation = new File(SoupApp.context.getFilesDir(), "INSTALLATION");
        try {
            if (!installation.exists()) {
                writeInstallationFile(installation);
            }
            id = readInstallationFile(installation);
            Log.e("trueinstallid", id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    //获取电话号码
    public static String getNativePhoneNumber(Context ctx) {
        return ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
    }
}
