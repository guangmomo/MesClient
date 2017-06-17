package com.xuliwen.mestest2.file.sp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xuliwen.mestest2.R;
import com.xuliwen.mestest2.utils.L;

public class SPSameProcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spsame_process);
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.l("SPSameProcessActivity: value: "+SpUtil.get());
    }

    public void onWriteClick(View view) {
        SpUtil.put("SameProcess2");
    }

    public void onReadClick(View view) {
        L.l("SPSameProcessActivity: value: "+SpUtil.get());
    }
}
