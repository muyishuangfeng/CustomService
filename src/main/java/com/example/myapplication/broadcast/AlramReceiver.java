package com.example.myapplication.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.service.MyService;

/**
 * Created by Silence on 2016/11/18.
 * 定时广播
 */

public class AlramReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mIntent = new Intent(context, MyService.class);
        context.startService(mIntent);
    }
}
