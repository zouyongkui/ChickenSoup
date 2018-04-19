package com.one.zyk.soup.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.one.zyk.soup.app.SoupApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Objects;
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
     *
     * @return
     */
    public static String getIMEI() {
        String id = "";
        if (ActivityCompat.checkSelfPermission(SoupApp.getInstance(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) SoupApp.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            id = tm.getDeviceId();
            if (!TextUtils.isEmpty(id)) {
                Log.e("deviceId", "IMEI" + id);
                return id;
            }
            String phone = getNativePhoneNumber();
            if (!TextUtils.isEmpty(phone)) {
                Log.e("deviceId", "phone" + id);
                return phone;
            }
        }
        File installation = new File(SoupApp.getInstance().getFilesDir(), "INSTALLATION");
        try {
            if (!installation.exists()) {
                writeInstallationFile(installation);
            }
            id = readInstallationFile(installation);
            id = id.replaceAll("-", "");
            Log.e("deviceId", "random" + id);
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
    @SuppressLint("HardwareIds")
    public static String getNativePhoneNumber() {
        String phone = "";
        if (ActivityCompat.checkSelfPermission(SoupApp.getInstance(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            phone = ((TelephonyManager) Objects.requireNonNull(SoupApp.getInstance().getSystemService(Context.TELEPHONY_SERVICE))).getLine1Number();
        }
        return phone;
    }

    @SuppressLint("HardwareIds")
    public static String getPhoneStatus() {
        TelephonyManager tm = (TelephonyManager) SoupApp.getInstance()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String str = "";
        if (ActivityCompat.checkSelfPermission(SoupApp.getInstance(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
            str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
            str += "Line1Number = " + tm.getLine1Number() + "\n";
            str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
            str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
            str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
            str += "NetworkType = " + tm.getNetworkType() + "\n";
            str += "PhoneType = " + tm.getPhoneType() + "\n";
            str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
            str += "SimOperator = " + tm.getSimOperator() + "\n";
            str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
            str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
            str += "SimState = " + tm.getSimState() + "\n";
            str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
            str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        }
        return str;
    }
    //        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        List<String> list = locationManager.getProviders(true);
//        if (list.contains(LocationManager.NETWORK_PROVIDER)) {
//            //是否为网络位置控制器
//            provider = LocationManager.NETWORK_PROVIDER;
//        } else {
//            Snackbar.make(coordinatorLayout, "请先打开网络定位哦！", Snackbar.LENGTH_LONG)
//                    .setAction("", null)
//                    .show();
//            return;
//        }
//        Location location = locationManager.getLastKnownLocation(provider);
//        if (location != null) {
//            //获取当前位置，这里只用到了经纬度
//            String string = "纬度为：" + location.getLatitude() + ",经度为：" + location.getLongitude();
//            Log.e("location", string);
//        }else {
//            Log.e("location", "null");
//
//        }
}
