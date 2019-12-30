package com.io.ioDemo;

import java.io.UnsupportedEncodingException;

/**
 * Created by Brian in 17:47 2018/4/1
 */
public class EncodeDemo {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "尝试ABC";
        byte[] bytes = s.getBytes();

        for (byte b: bytes){
            System.out.print(Integer.toHexString(b & 0xff)+" ");

        }

        try {
            byte[] bytes2 = s.getBytes("utf-8");
            System.out.println();
            System.out.println("== utf-8 ==");
            for (byte b: bytes2){

                System.out.print(Integer.toHexString(b & 0xff)+" ");
            }


            byte[] bytes23 = s.getBytes("utf-16be");
            System.out.println();
            System.out.println("== utf-16be ==");
            for (byte b: bytes23){
                System.out.print(Integer.toHexString(b & 0xff)+" ");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

      /*   gbk 编码中文占2个字节，英文占1个字节
         utf-8 编码中文占3个字节，英文占1个字节
         java 是双字节编码utf-16be 编码

        文本文件是任意序列的字节序列，如果在中文机器上创建文本文件，
        那么该文件只认识ansi编码。*/
    }
}
