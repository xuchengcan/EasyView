package chen.easyview.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;

import com.socks.library.KLog;

import java.util.List;

import chen.easyview.R;
import chen.easyview.aidl.Book;
import chen.easyview.aidl.IBookManager;
import chen.easyview.aidl.IOnNewBookArrivedListener;
import chen.easyview.base.MyConstants;
import chen.easyview.service.BookManagerService;

public class BookManagerActivity extends AppCompatActivity {

    private IBookManager mRemoteBookManager;

    private static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyConstants.MESSAGE_NEW_ARRIVED:
                    KLog.e("receive new book :" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private static MyHandler mHandler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        bindService(new Intent(BookManagerActivity.this, BookManagerService.class),mConnection,BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                mRemoteBookManager = bookManager;
                List<Book> list = bookManager.getBookList();
                KLog.e(list.toString());
                KLog.e("query book list type:"+list.getClass().getCanonicalName());//获取该类的类型
                Book newBook = new Book(3,"Android glide");
                bookManager.addBook(newBook);
                bookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private static IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MyConstants.MESSAGE_NEW_ARRIVED, newBook).sendToTarget();
        }
    };

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()){
            try {
                KLog.e("unregister listener:" + mOnNewBookArrivedListener);
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        mHandler = null;
        super.onDestroy();
    }

}
