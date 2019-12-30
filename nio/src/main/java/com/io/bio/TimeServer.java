package com.io.bio;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by Brian in 18:12 2018/4/25
 */
public class TimeServer {


    public static void main(String[] args) {
        int port = 9030;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            Socket socket = null;

            while (true) {
                // 当没有客户端连接时，程序会阻塞在accept这里，当有客户端访问时，就会创建新的线程去重新执行
                socket = serverSocket.accept();
                // 每有一个客户端访问，就添加一个线程执行
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket = null;
            }
        }
    }

}
