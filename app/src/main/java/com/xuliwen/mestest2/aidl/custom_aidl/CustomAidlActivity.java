package com.xuliwen.mestest2.aidl.custom_aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xuliwen.mestest2.Constants;
import com.xuliwen.mestest2.R;
import com.xuliwen.mestest2.utils.L;

public class CustomAidlActivity extends AppCompatActivity {

    private static final int TRANSACTION_add=0;
    private static final String DESCRIPTOR = "com.xuliwen.mestest1.aidl.custom_aidl.CustomAidlService";
    private static final String ACTION="com.xuliwen.mestest1.aidl.custom_aidl.CustomAidlService";
    private IBinder binder;

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder=service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_aidl);
    }



    private int remoteAdd(int arg1,int arg2){
        int result=0;
        Parcel data=Parcel.obtain();
        Parcel reply=Parcel.obtain();
        try{
            data.writeInterfaceToken(DESCRIPTOR);
            data.writeInt(arg1);
            data.writeInt(arg2);
            binder.transact(TRANSACTION_add,data,reply,0);
            reply.readException();
            result=reply.readInt();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
        return result;
    }

    public void bindAidlService(View view) {
        Intent intent=new Intent(ACTION);
        intent.setPackage(Constants.PKG_NAME);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        L.l("绑定远程服务");
    }

    public void invokeAidlService(View view) {
        if(binder==null){
            L.l("请先绑定远程服务");
        }else{
            int result=remoteAdd(2,3);
            L.l("CustomAidlService，2+3="+result);
        }
    }

    public void unbindAidlService(View view) {
        if(binder!=null){
            unbindService(serviceConnection);
            binder=null;    //如果调用unbindService后，没有将binder置为null，则依旧可以通过binder去调用服务进程
            L.l("解绑远程服务");
        }
    }
}
