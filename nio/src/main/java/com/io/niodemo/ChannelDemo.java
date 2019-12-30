package com.io.niodemo;

import com.io.niodemo.fileUtil.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by Brian in 20:24 2018/5/2
 */
public class ChannelDemo {
    public static void main(String[] args) throws IOException{
        String relativePath = System.getProperty("user.dir")+"/file_dir";
        File fileRelative = new File(relativePath);

        FileUtil.createFile(fileRelative, "directory");
        File in = new File(relativePath+"/testin.txt");
        // 文件集中处理，目录或文件都判断新建
        FileUtil.createFile(in, "file");
        File out = new File(relativePath+"/testout.txt");
        FileUtil.createFile(out, "file");

        FileInputStream input = new FileInputStream(in.toString());
        FileOutputStream outPut = new FileOutputStream(out.toString());

        ReadableByteChannel source = input.getChannel();
        WritableByteChannel destination = outPut.getChannel();

        copyData(source, destination);
        source.close();
        destination.close();

    }

    private static void copyData(ReadableByteChannel src, WritableByteChannel dest) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocateDirect(20*1024);
        while(src.read(buffer) != -1){
            // The buffer is used to drained
            buffer.flip();

            // keep sure that buffer was fully drained
            while(buffer.hasRemaining()){
                dest.write(buffer);
            }

            // now the buffer is empty, ready for the filling
            buffer.clear();
        }
    }


}
