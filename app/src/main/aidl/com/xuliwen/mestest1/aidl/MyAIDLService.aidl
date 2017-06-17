// MyAIDLService.aidl
package com.xuliwen.mestest1.aidl;
import com.xuliwen.mestest1.aidl.Student;

// Declare any non-default types here with import statements

interface MyAIDLService {

    int add(int arg1, int arg2);

    int minus(int arg1, int arg2);

     String inStudentInfo(in Student student);

        String outStudentInfo(out Student student);

        String inOutStudentInfo(inout Student student);

}
