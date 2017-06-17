package com.xuliwen.mestest2.file.common_file;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xuliwen.mestest2.Constants;
import com.xuliwen.mestest2.R;
import com.xuliwen.mestest2.utils.SDCardUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReadActivity extends AppCompatActivity {

    private TextView readFileTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_read);
        readFileTextView = (TextView) findViewById(R.id.file_read_textView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void readFromFile(View view) {
     //   readFromFileJava7();
        String filePath = SDCardUtils.getSDCardPath() + Constants.AcrossFileName;
        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);
            char[] bufferChar = new char[1024];
            int length = -1;
            try {
                while ((length = bufferedReader.read(bufferChar)) != -1) {
                    content.append(bufferChar, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        readFileTextView.setText(content.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void readFromFileJava7() {
        String filePath = SDCardUtils.getSDCardPath() + Constants.AcrossFileName;
        StringBuilder content = new StringBuilder();
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            char[] bufferChar = new char[1024];
            int length;
            while ((length = bufferedReader.read(bufferChar)) != -1) {
                content.append(bufferChar, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        readFileTextView.setText(content.toString());
    }
}
