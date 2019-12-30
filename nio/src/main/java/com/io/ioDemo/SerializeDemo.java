package com.io.ioDemo;

import java.io.*;

/**
 * Created by Brian in 22:38 2018/4/1
 */
public class SerializeDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
       /* File file = new File("E:/tmp/student.txt");
        FileWriter fileWriter =  new FileWriter(file);
        fileWriter.write("");
        fileWriter.close();

        Student student = new Student(12, "Tom", "26alge", "test");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(student);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Student student1 = (Student) ois.readObject();
        System.out.println(student1.toString());

        ois.close();*/
        testReturn();
    }


    public static void testReturn(){
        System.out.println("空返回 ==");

//        throw new RuntimeException(" throw Exception test");

        return;

    }

}
