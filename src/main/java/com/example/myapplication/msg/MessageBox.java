package com.example.myapplication.msg;

/**
 * Created by Silence on 2016/11/18.
 */

public class MessageBox {
    public static final int BASE_MESSAGE = 0X01;
    //获取定位信息
    public static final int GET_GPS_MSG_SU = BASE_MESSAGE + 1;
    //获取定位信息失败
    public static final int GET_GPS_MSG_ERROR = BASE_MESSAGE + 2;
}
