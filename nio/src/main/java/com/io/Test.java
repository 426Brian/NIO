package com.io;

import java.io.*;

/**
 * Created by Brian in 21:56 2018/11/23
 */
public class Test {
    public static void main(String[] args) {
        FileInputStream fis =null;
        FileOutputStream fos =null;
        try {
            fis = new FileInputStream(new File("E:/download/browser/jdk-10.0.2_windows-x64_bin.exe"));

            int size = 0;
            int count = 0;
            byte[] bf = new byte[1024];
            while((size = fis.read(bf)) > 0){
                count++;
                System.out.println(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
