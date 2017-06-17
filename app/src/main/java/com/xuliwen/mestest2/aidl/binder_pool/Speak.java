package com.xuliwen.mestest2.aidl.binder_pool;

import android.os.RemoteException;
import android.util.Log;

import com.xuliwen.mestest1.aidl.ISpeak;

public class Speak extends ISpeak.Stub {
    public Speak() {
        Log.d("cylog","我被实例化了..............");
    }
    @Override
    public void speak() throws RemoteException {
        int pid = android.os.Process.myPid();
        Log.d("cylog","当前进程ID为："+pid+"-----"+"这里收到了客户端的speak请求");
    }
}
