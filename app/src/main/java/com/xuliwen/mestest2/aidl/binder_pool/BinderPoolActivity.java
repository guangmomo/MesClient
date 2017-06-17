package com.xuliwen.mestest2.aidl.binder_pool;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xuliwen.mestest1.aidl.IBinderPool;
import com.xuliwen.mestest1.aidl.ICalculate;
import com.xuliwen.mestest1.aidl.ISpeak;
import com.xuliwen.mestest2.Constants;
import com.xuliwen.mestest2.MainActivity;
import com.xuliwen.mestest2.R;

public class BinderPoolActivity extends AppCompatActivity {

    private final static String ACTION="com.xuliwen.mestest1.aidl.binder_pool.BinderPoolService";
    private ISpeak mSpeak;
    private ICalculate mCalculate;
    private int pid = android.os.Process.myPid();
    private IBinderPool binderPool;

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, final IBinder service) {
            new Thread(){
                @Override
                public void run() {
                    binderPool=IBinderPool.Stub.asInterface(service);
                    Log.d("cylog","获取speakBinder对象...........");
                    IBinder speakBinder = null;  // 2
                    try {
                        speakBinder = binderPool.queryBinder(Constants.BINDER_SPEAK);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Log.d("cylog","获取speak的代理对象............");
                    mSpeak =  ISpeak.Stub.asInterface(speakBinder);    // 3
                    try {
                        mSpeak.speak();     // 4
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Log.d("cylog","获取calculateBinder对象...........");
                    IBinder calculateBinder = null;
                    try {
                        calculateBinder = binderPool.queryBinder(Constants.BINDER_CALCULATE);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Log.d("cylog","获取calculate的代理对象............");
                    mCalculate =  ICalculate.Stub.asInterface(calculateBinder);
                    try {
                        Log.d("cylog",""+mCalculate.add(5,6));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        startWork();
    }

    private void startWork() {
        Log.d("cylog","当前进程ID为："+pid);
        Log.d("cylog","获取BinderPool对象............");
        Intent intent=new Intent(ACTION);
        intent.setPackage(Constants.PKG_NAME);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

}
