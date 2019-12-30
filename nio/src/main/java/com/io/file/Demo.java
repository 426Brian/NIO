package com.io.file;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Brian in 0:08 2018/11/20
 */
public class Demo {
    public static void main(String[] args) {
        File file = new File("C:/Users/Brian/Desktop/demo.txt");
        readTest(file);
    }

    public static void readTest(File source) {
        FileReader reader = null;
        try {
            reader = new FileReader(source);
            char[] bt = new char[5];
            int len;
            String str;
            while ((len = reader.read(bt)) != -1){
                str = new String(bt, 0, len);
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
