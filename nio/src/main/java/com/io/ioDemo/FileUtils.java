package com.io.ioDemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Brian in 20:22 2018/4/1
 */
public class FileUtils {
    public static void main(String[] args){
        File file = new File("E:/tmp/1515075921743closejdk.bat");

        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            System.out.println("指针位置："+raf.getFilePointer());
            raf.write('A');
            System.out.println("指针位置："+raf.getFilePointer());
            raf.write('B');

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listDirectory(File dir){
        if(!dir.exists()){
           throw new IllegalArgumentException("目录 "+dir+" 不存在");
        }

        if(!dir.isDirectory()){
            throw new IllegalArgumentException(dir+" 不是目录");
        }

    }


}
