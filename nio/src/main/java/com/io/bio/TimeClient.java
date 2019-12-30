package com.io.bio;

import java.io.*;
import java.net.Socket;

/**
 * Created by Brian in 22:04 2018/4/25
 */
public class TimeClient {


    public static void main(String[] args) {
        int port = 9030;
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        String res = null;
        try {
            while(true){
                socket = new Socket("127.0.0.1", port);

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

                // 发送请求
                out.println("[client] this is request from client");

                // 获得响应
                res = in.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(socket !=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
