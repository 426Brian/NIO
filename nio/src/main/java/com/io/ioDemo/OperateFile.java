package com.io.ioDemo;

import java.io.*;

/**
 * Created by Brian in 22:12 2018/4/1
 */
public class OperateFile {
    public static void main(String[] args) throws IOException {
        FileInputStream in = new FileInputStream(new File("E:/tmp/1515075921743closejdk.bat"));

//        byte[] bytes = new byte[1024];
//        System.out.println(in.read(bytes));
//        while(in.read(bytes) != -1){
//            System.out.println(Arrays.toString(bytes));
//        }


        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader bfreader = new BufferedReader(reader);

        String str = null;

        while((str = bfreader.readLine()) != null){
           System.out.println(str);
        }

        reader.close();
        in.close();
    }
}
