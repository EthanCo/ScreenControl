package com.ethanco.screen_control;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Receiver class which shows notifications when the Device Administrator status
 * of the application changes.
 */
public class ScreenAdminReceiver extends DeviceAdminReceiver {
    public static final String TAG = "Z-ScreenOffAdmin";

    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.i(TAG, "设备管理器使用");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        Log.i(TAG, "设备管理器没有使用");
    }

}
