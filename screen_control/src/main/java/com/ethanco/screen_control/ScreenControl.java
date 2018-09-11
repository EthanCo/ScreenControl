package com.ethanco.screen_control;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

import static android.content.Context.POWER_SERVICE;

/**
 * 熄屏 亮屏
 *
 * @author EthanCo
 * @since 2018/9/11
 */

public class ScreenControl {
    private static final String TAG = "Z-ScreenControl";
    private PowerManager mPowerManager;
    private ComponentName adminReceiver;
    private DevicePolicyManager policyManager;

    private ScreenControl() {
    }

    private static class SingleTonHolder {
        private static ScreenControl sInstance = new ScreenControl();
    }

    public static ScreenControl getInstance() {
        return SingleTonHolder.sInstance;
    }

    public ScreenControl init(Context context) {
        return init(context, ScreenAdminReceiver.class);
    }

    public ScreenControl init(Context context,Class<?> receiverClass) {
        adminReceiver = new ComponentName(context, receiverClass);
        mPowerManager = (PowerManager) context.getSystemService(POWER_SERVICE);
        policyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
         return this;
    }

    public static final int SCREEN_REQUEST_CODE = 15471;

    /**
     * 亮屏
     */
    public void turnOn() {
        Log.i(TAG, "turnOn");
        PowerManager.WakeLock mWakeLock = mPowerManager.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        mWakeLock.acquire();
        mWakeLock.release();
    }

    /**
     * 熄屏
     */
    public void turnOff() {
        Log.i(TAG, "turnOff");
        policyManager.lockNow();
    }

    /**
     * 请求设备管理器权限
     *
     * @param activity
     * @param explain
     */
    public void openScreenPermission(Activity activity, String explain) {
        if (TextUtils.isEmpty(explain)) {
            explain = "程序操作需要相应权限，请激活设备管理器";
        }

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminReceiver);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, explain);
        activity.startActivityForResult(intent, SCREEN_REQUEST_CODE);
    }

    /**
     * 设备是否已被激活
     *
     * @return
     */
    public boolean isAdminActive() {
        return policyManager.isAdminActive(adminReceiver);
    }
}
