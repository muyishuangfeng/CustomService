package com.example.myapplication.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.example.myapplication.bean.GetGpsBean;
import com.example.myapplication.broadcast.AlramReceiver;
import com.example.myapplication.msg.MessageBox;
import com.example.myapplication.thread.GetGpsThread;
import com.example.myapplication.util.ToastUtils;

import java.util.logging.Logger;


/**
 * Created by Silence on 2016/11/18.
 */

public class MyService extends Service {

    public Log mLog;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getGpsLocation();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = 10 * 1000;
        long triggerTime = SystemClock.elapsedRealtime() + time;
        Intent intent2 = new Intent(this, AlramReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent2, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * handler
     */
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageBox.GET_GPS_MSG_ERROR: {//定位失败
                    ToastUtils.showToast(MyService.this, "定位失败");
                }
                break;
                case MessageBox.GET_GPS_MSG_SU: {//定位成功
                    GetGpsBean gpsBean = (GetGpsBean) msg.obj;
                    Log.i("天下为公", gpsBean.getCityName()+"");
                    ToastUtils.showToast(MyService.this, gpsBean.getCityName());

                }
                break;
            }
        }
    };

    /**
     * 获取定位信息
     */
    public void getGpsLocation() {
        GetGpsThread mThread = new GetGpsThread(MyService.this, mHandler, "50","123");
        mThread.start();

    }
}
