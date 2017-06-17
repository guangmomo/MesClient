// IOnNewBookArrivedListener.aidl
package com.xuliwen.mestest1.aidl;

// Declare any non-default types here with import statements
import com.xuliwen.mestest1.aidl.Book;

interface IOnNewBookArrivedListener {
   void onNewBookArrived(in Book book);
}
