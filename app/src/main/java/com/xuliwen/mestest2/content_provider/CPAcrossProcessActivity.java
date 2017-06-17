package com.xuliwen.mestest2.content_provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.xuliwen.mestest2.Constants;
import com.xuliwen.mestest2.R;
import com.xuliwen.mestest2.utils.L;

import java.util.ArrayList;
import java.util.List;

public class CPAcrossProcessActivity extends AppCompatActivity {

    private EditText studentName;
    private EditText studentHeight;
    private static final Uri INSERT_URL = Uri.parse("content://com.xuliwen.mestest1.content_provider.StudentContentProvider/insert");
    private static final Uri DELETE_URL = Uri.parse("content://com.xuliwen.mestest1.content_provider.StudentContentProvider/delete");
    private static final Uri UPDATE_URL = Uri.parse("content://com.xuliwen.mestest1.content_provider.StudentContentProvider/update");
    private static final Uri QUERY_URL = Uri.parse("content://com.xuliwen.mestest1.content_provider.StudentContentProvider/query");
    private static final Uri STUDENT_URL = Uri.parse("content://com.xuliwen.mestest1.content_provider.StudentContentProvider");
    private ContentResolver contentResolver;
    private StudentContentObserver contentObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpacross_process);
        initContentResolver();
        initView();
    }

    private void initContentResolver(){
        contentResolver = getContentResolver();
        contentObserver=new StudentContentObserver(new Handler());
        contentResolver.registerContentObserver(STUDENT_URL, true,contentObserver);
    }

    private void initView() {
        studentName = (EditText) findViewById(R.id.student_name_editText);
        studentHeight = (EditText) findViewById(R.id.student_height_editText);
    }

    public void addStudent(View view) {
        ContentValues values = new ContentValues();
        values.put(Constants.NAME, studentName.getText().toString());
        values.put(Constants.HEIGHT, Float.valueOf(studentHeight.getText().toString()));
        contentResolver.insert(INSERT_URL, values);
    }

    public void printStudentTable() {
        List<Student> students = new ArrayList<>();
        Cursor cursor = contentResolver.query(QUERY_URL, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Student student = new Student();
                student.setName(cursor.getString(cursor.getColumnIndex(Constants.NAME)));
                student.setHeight(cursor.getFloat(cursor.getColumnIndex(Constants.HEIGHT)));
                students.add(student);
            }
            cursor.close();
        }
        int size = students.size();
        L.l("打印Student表如下：");
        for (int i = 0; i < size; i++) {
            L.l("第" + i + "条数据为： " + students.get(i));
        }
    }


    public void deleteStudentByName(View view) {
        String name = studentName.getText().toString();
        float height = Float.parseFloat(studentHeight.getText().toString());
        contentResolver.delete(DELETE_URL, "name = ? and height = ?", new String[]{name, String.valueOf(height)});
    }

    public void updateStudentByName(View view) {
        ContentValues values = new ContentValues();
        values.put(Constants.NAME, studentName.getText().toString());
        values.put(Constants.HEIGHT, Float.valueOf(studentHeight.getText().toString()));
        contentResolver.update(UPDATE_URL, values, "name = ?", new String[]{studentName.getText().toString()});
    }

    public void printAllStudent(View view) {
        printStudentTable();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(contentResolver!=null){//在这里解除监听，否则会造成内存泄漏
            contentResolver.unregisterContentObserver(contentObserver);
        }

    }

    private class StudentContentObserver extends ContentObserver {
        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public StudentContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            L.l("监听到服务器的数据发生改变");
            printStudentTable();
        }
    }






}
