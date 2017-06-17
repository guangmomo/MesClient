package com.xuliwen.mestest2.file.sp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xuliwen.mestest2.R;
import com.xuliwen.mestest2.utils.L;

public class SharedPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharepreference1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.l("SharedPreferencesActivity: value: "+SpUtil.get());
    }

    public void onWriteClick(View view) {
       SpUtil.put("SameProcess1");
    }

    public void onReadClick(View view) {
        L.l("SharedPreferencesActivity: value: "+SpUtil.get());
    }

    public void onStartOtherProcessActivity(View view) {
        startActivity(new Intent(this,SPOtherProcessActivity.class));
    }



    public void onStartSameProcessActivity(View view) {
        startActivity(new Intent(this,SPSameProcessActivity.class));
    }
}
