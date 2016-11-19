package com.example.myapplication.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.myapplication.bean.GetGpsBean;
import com.example.myapplication.msg.MessageBox;


public class GetGpsThread extends Thread {

    private final MyLocationListenner myListener = new MyLocationListenner();
    private final Handler handler;
    private final LocationClient mLocClient;
    private final String getgpstime;
    private final Context context;
    private final String userno;

    public GetGpsThread(Context context, Handler handler, String getgpstime,
                        String userno) {
        super();
        this.handler = handler;
        this.getgpstime = getgpstime;
        this.context = context;
        this.userno = userno;
        mLocClient = new LocationClient(context);
        mLocClient.registerLocationListener(myListener);
    }

    @Override
    public void run() {
        super.run();

        InitLocation();
        // setLocationOption();
        if (mLocClient.isStarted()) {
            mLocClient.stop();
        }
        mLocClient.start();

    }

    private void sendMessage(int what, Object obj) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        handler.sendMessage(msg);
    }

    protected void onStop() {
        mLocClient.stop();
        super.stop();
    }

    private class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null
                    || location.getLocType() == BDLocation.TypeServerError) {
                sendMessage(MessageBox.GET_GPS_MSG_ERROR, null);
                return;
            }

            GetGpsBean getgpsinfo = new GetGpsBean();
            getgpsinfo.setCityName(location.getCity());
            getgpsinfo.setLongitude(location.getLongitude());
            getgpsinfo.setLatitude(location.getLatitude());
            getgpsinfo.setAddress(location.getAddress().address);
            sendMessage(MessageBox.GET_GPS_MSG_SU, getgpsinfo);
            mLocClient.stop();

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    // 定位参数设置
    // private void setLocationOption() {
    // LocationClientOption option = new LocationClientOption();
    // option.setOpenGps(true); // 打开gps
    //
    // option.setCoorType("bd09ll"); // 设置坐标类型
    // // option.setServiceName("com.baidu.location.service_v2.9");
    // option.setPoiExtraInfo(true);
    //
    // option.setAddrType("all");
    //
    // if (getgpstime.equals("0")) {
    // option.setScanSpan(900);
    // } else {
    // int ls_gpstime = Integer.valueOf(getgpstime);
    // option.setScanSpan(1000 * ls_gpstime);
    // }
    //
    //
    // // option.setPriority(LocationClientOption.NetWorkFirst); //设置网络优先
    //
    // option.setPriority(LocationClientOption.GpsFirst); // 不设置，默认是gps优先
    //
    // option.setPoiNumber(5);
    // option.disableCache(true);
    // mLocClient.setLocOption(option);
    // }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
        option.setOpenGps(true); // 打开gps
        option.setScanSpan(50);// 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);

    }

}
