package com.xuliwen.mestest2.content_provider;

/**
 * Created by xlw on 2017/6/1.
 */

public class Student {
    private String name;
    private float height;

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
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
                "height=" + height +
                ", name='" + name + '\'' +
                '}';
    }
}
