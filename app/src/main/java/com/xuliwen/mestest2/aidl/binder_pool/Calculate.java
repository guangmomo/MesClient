package com.xuliwen.mestest2.aidl.binder_pool;

import android.os.RemoteException;
import android.util.Log;

import com.xuliwen.mestest1.aidl.ICalculate;

/**
 * Created by xlw on 2017/5/28.
 */

public class Calculate extends ICalculate.Stub {
    @Override
    public int add(int num1, int num2) throws RemoteException {
        int pid = android.os.Process.myPid();
        Log.d("cylog", "当前进程ID为："+pid+"----"+"这里收到了客户端的Calculate请求");
        return num1+num2;
    }
}