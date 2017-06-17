// IBookManager.aidl
package com.xuliwen.mestest1.aidl;

// Declare any non-default types here with import statements
import com.xuliwen.mestest1.aidl.Book;
import com.xuliwen.mestest1.aidl.IOnNewBookArrivedListener;

interface IBookManager {
   List<Book> getBookList();
   void addBook(in Book book);
   void registerListener(IOnNewBookArrivedListener listener);
   void unregisterListener(IOnNewBookArrivedListener listener);
}
