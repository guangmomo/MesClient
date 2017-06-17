package com.xuliwen.mestest2.remote_view;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.xuliwen.mestest2.Constants;
import com.xuliwen.mestest2.R;

public class SendRemoteViewActivity extends AppCompatActivity {

    private static final String RECEIVER_ACTION="com.xuliwen.mestest1.remote_view.ReceiveRemoteViewActivity.BroadcastReceiver";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_remote_view);

//        new Thread(){
//            @Override
//            public void run() {
//                SendRemoteViewActivity.this.startActivity(new Intent(SendRemoteViewActivity.this,ReceiveRemoteViewActivity.class));
//                try {
//                    Thread.sleep(4000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                SendRemoteViewActivity.this.sendRemoteView(null);
//            }
//        }.start();
    }

    public void sendRemoteView(View view) {
        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.layout_remote_views);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,new Intent(this,SendRemoteViewActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.remoteView_button,pendingIntent);
        Intent intent=new Intent(RECEIVER_ACTION);
     //   intent.setPackage(getPackageName());
        intent.setPackage(Constants.PKG_NAME);
        intent.putExtra("remoteViews",remoteViews);
        sendBroadcast(intent);
    }
}
