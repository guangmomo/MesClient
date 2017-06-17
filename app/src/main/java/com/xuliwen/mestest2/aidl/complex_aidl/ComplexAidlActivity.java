package com.xuliwen.mestest2.aidl.complex_aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xuliwen.mestest1.aidl.Book;
import com.xuliwen.mestest1.aidl.IBookManager;
import com.xuliwen.mestest1.aidl.IOnNewBookArrivedListener;
import com.xuliwen.mestest2.Constants;
import com.xuliwen.mestest2.R;
import com.xuliwen.mestest2.utils.L;

import org.w3c.dom.Text;

import java.util.List;

public class ComplexAidlActivity extends AppCompatActivity {

    private static final String TAG="ComplexAidlActivity  ";
    private static final String ACTION="com.xuliwen.mestest1.aidl.complex_aidl.BookManagerService";
    private IBookManager iBookManager;
    private TextView complexAidlTextView;
    private Handler uiHandler=new Handler();


    private IOnNewBookArrivedListener.Stub newBookArrivedListener=new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(final Book book) throws RemoteException {//运行在Binder线程池中
            L.l(TAG+ "onNewBookArrived线程: "+Thread.currentThread().getId());
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    complexAidlTextView.setText("新书名字为： "+book.getName());
                }
            });
        }
    };

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager=IBookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(deathRecipient,0);//设置service进程死亡的监听
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            L.l(TAG+"绑定到BookManagerService");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iBookManager=null;
//            bindBookManagerService(null);
//            L.l(TAG+"服务端进程死亡，尝试重新绑定到BookManagerService");
        }
    };

    private IBinder.DeathRecipient deathRecipient=new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            bindBookManagerService(null);
            L.l(TAG+"服务端进程死亡，尝试重新绑定到BookManagerService");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_aidl);
        L.l(TAG+ "UI线程: "+ Thread.currentThread().getId());
        complexAidlTextView= (TextView) findViewById(R.id.complex_aidl_textView);
    }

    public void bindBookManagerService(View view) {
        Intent intent=new Intent(ACTION);
        intent.setPackage(Constants.PKG_NAME);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    public void unbindBookManagerService(View view) {
        if(iBookManager!=null && iBookManager.asBinder().isBinderAlive()){
            try {
                iBookManager.unregisterListener(newBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            unbindService(serviceConnection);
        }
        L.l(TAG+"解绑BookManagerService");
    }

    public void getBookList(View view) throws RemoteException {
        List<Book> bookList=iBookManager.getBookList();
        printBookList(bookList);
    }

    //因为AIDL的调用是同步调用，当service的方法要执行耗时任务才能返回结果时，
    // 将会堵塞UI线程，所以要在子线程中对service的方法进行调用
    public void addBook(View view) throws RemoteException {
        new Thread(){
            @Override
            public void run() {
                List<Book> bookList=null;
                try {
                    iBookManager.addBook(new Book("艺术探索"));
                    bookList=iBookManager.getBookList();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                printBookList(bookList);
            }
        }.start();

    }


    private void printBookList(List<Book> bookList){
        for(Book book:bookList){
            L.l(TAG+"bookName: "+book.getName());
        }
    }

    public void addNewBookListener(View view) throws RemoteException {
        iBookManager.registerListener(newBookArrivedListener);
    }

    public void removeNewBookListener(View view) throws RemoteException {
        iBookManager.unregisterListener(newBookArrivedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //iBookManager.asBinder().isBinderAlive()判断服务端进程是否还在运行
        if(iBookManager!=null && iBookManager.asBinder().isBinderAlive()){
            try {
                iBookManager.unregisterListener(newBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            unbindService(serviceConnection);
        }
    }
}
