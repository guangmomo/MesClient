package com.xuliwen.mestest2.messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xuliwen.mestest1.aidl.Student;
import com.xuliwen.mestest2.Constants;
import com.xuliwen.mestest2.R;
import com.xuliwen.mestest2.utils.L;

public class MessengerActivity extends AppCompatActivity {

    private static final String ACTION="com.xuliwen.mestest1.messenger.MessengerService";

    private Messenger serviceMessenger;

    private Handler clientHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int result=msg.arg1;
            L.l("Messenger: 2+3== "+result);
        }
    };

    private Messenger clientMessenger=new Messenger(clientHandler);

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceMessenger =new Messenger(service);
            L.l("bind MessengerService");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messager);
    }

    public void bindMessengerService(View view) {
        Intent intent=new Intent(ACTION);
        intent.setPackage(Constants.PKG_NAME);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    /**
     * 调用远程service做加法运算
     * @param view
     */
    public void invokeMessengerService(View view) {
        Message message=Message.obtain();

        //使用Messenger来传递对象是不行的,因为传递过去后，由于类加载器不一样，会提示找不到Student
//        Student student=new Student();
//        student.setName("徐立文");
//        student.setAge(22);

        Bundle bundle=new Bundle();
        bundle.putInt("bundleTestInt",1);
        bundle.putString("bundleTestString","String");
        message.setData(bundle);

//        message.obj=student;
        message.arg1=2;
        message.arg2=3;
        message.replyTo=clientMessenger;
        try {
            serviceMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unbindMessengerService(View view) {
        unbindService(serviceConnection);
        L.l("unbind MessengerService");
    }
}
