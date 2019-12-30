package com.io.pseudo;

import com.io.bio.TimeServerHandler;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 创建一个时间服务器处理类的线程池，当接收到新的客户端连接时，
 * 将请求socket 封装成一个task, 然后调用线程池的execute 方法执行，
 * 从而建避免了每个请求接入都创一个新的线程
 * Created by Brian in 18:12 2018/4/25
 */
public class TimeServer {
    private static final Log4JLogger log = (Log4JLogger) LogFactory.getLog(TimeServer.class);

    public static void main(String[] args) {
        int port = 9030;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            log.info("[server] is listening port "+port);
            Socket socket = null;
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50, 10000);

            while(true){
                socket = serverSocket.accept();
                singleExecutor.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket !=null){
                try {
                    serverSocket.close();
                    log.info("[server] close");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket =null;
            }
        }
    }

}
