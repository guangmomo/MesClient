package com.xuliwen.mestest1.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xlw on 2017/5/20.
 */

public class Student implements Parcelable{
    private int age;
    private String name;


    protected Student(Parcel in) {
        age = in.readInt();
        name = in.readString();
    }

    /**
     * Student需要提供一个无参数的构造器，这样aidl文件才不会报错
     */
    public Student(){}

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public void readFromParcel(Parcel source) {
        age = source.readInt();
        name = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(name);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
