package com.sharenew.screenoff;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ethanco.screen_control.ScreenAdminReceiver;
import com.ethanco.screen_control.ScreenControl;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Z-MainActivity";

    private ScreenControl screenControl = ScreenControl.getInstance()
            .init(App.getInstance(), ScreenAdminReceiver.class);
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    screenControl.turnOn();
                    break;
                case 2:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonOff = (Button) findViewById(R.id.button_off);

        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (screenControl.isAdminActive()) {
                    screenControl.turnOff();
                    handler.sendEmptyMessageDelayed(1, 3000);
                } else {
                    Toast.makeText(MainActivity.this, "没有设备管理权限", Toast.LENGTH_LONG).show();
                    handler.sendEmptyMessageDelayed(1, 3000);
                }
            }
        });

        Button buttonOn = (Button) findViewById(R.id.button_on);
        buttonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenControl.turnOn();
            }
        });

        if (!screenControl.isAdminActive()) {
            screenControl.openScreenPermission(this, "程序操作需要相应权限，请激活设备管理器");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //screenUtil.handleActivityResult(this);
        if (requestCode == ScreenControl.SCREEN_REQUEST_CODE) {
            if (screenControl.isAdminActive()) {
                Log.i(TAG, "设备已被激活");
            } else {
                Log.i(TAG, "设备没有被激活");
            }
        }
    }
}
