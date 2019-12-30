package com.io.niodemo;

import com.io.niodemo.fileUtil.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by Brian in 19:26 2018/5/3
 */
public class TransferDemo {
    public static void main(String[] args) throws IOException{
        String relativePath = System.getProperty("user.dir");
        String filePath = relativePath+"/file_dir";
        FileUtil.createFile(new File(filePath), "directory");

        String[] files = new String[]{"/testin1.txt", "/testin2.txt", "/testin3.txt", "/testin4.txt"};

        FileOutputStream output = new FileOutputStream(new File(filePath+"/combine.txt"));

        WritableByteChannel targetChannel = output.getChannel();

        for(String str : files){
            FileUtil.createFile(new File(filePath+str), "file");
            FileInputStream input = new FileInputStream(filePath+str);

            FileChannel inChannel = input.getChannel();
            inChannel.transferTo(0, inChannel.size(), targetChannel);

            inChannel.close();
            input.close();
        }

        targetChannel.close();
        output.close();
    }
}
