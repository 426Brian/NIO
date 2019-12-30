package com.io.ioDemo;

import java.io.Serializable;

/**
 * Created by Brian in 22:39 2018/4/1
 */
public class Student implements Serializable {
    private int age;
    private String name;
    private transient String password;
    private String no;

    public Student(int age, String name, String password, String no) {
        this.age = age;
        this.name = name;
        this.password = password;
        this.no = no;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", no='" + no + '\'' +
                '}';
    }
}
