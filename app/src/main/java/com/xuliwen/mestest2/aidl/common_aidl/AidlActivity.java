package com.xuliwen.mestest2.aidl.common_aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xuliwen.mestest1.aidl.MyAIDLService;
import com.xuliwen.mestest1.aidl.Student;
import com.xuliwen.mestest2.R;
import com.xuliwen.mestest2.utils.L;

public class AidlActivity extends AppCompatActivity {

    private MyAIDLService stub;//实际上他的运行时类型是Proxy

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            stub=MyAIDLService.Stub.asInterface(service);
            if(stub==null){
                L.l("stub==null");
            }else{
                try {
                    Student student=new Student();
                    student.setAge(12);
                    student.setName("徐立文");
                    L.l("inStudentInfo"+(stub.inStudentInfo(student)));

                    //下面传入的参数无效，因为out类型的方法，参数只能由服务端进行赋值，客户端赋值无效
                    student.setAge(12);
                    student.setName("徐立文");
                    L.l("outStudentInfo"+(stub.outStudentInfo(student)));

                    //student的参数值只能用一次，所以在in函数使用过后，在inOut函数之前还要再进行赋值
                    student.setAge(12);
                    student.setName("徐立文");
                    L.l("inOutStudentInfo"+(stub.inOutStudentInfo(student)));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        Intent intent=new Intent();
        intent.setAction("com.xuliwen.mestest1.RemoteService");
        intent.setPackage("com.xuliwen.mestest1");
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }
}
