package com.io.nio;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;


/**
 *
 * Created by Brian in 18:12 2018/4/25
 */
public class TimeServer {
    private static final Log4JLogger log = (Log4JLogger) LogFactory.getLog(TimeServer.class);

    public static void main(String[] args) {
        int port = 9030;

        log.info("[server] is listening port "+port);
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);

        // 创建Reactor 线程，并启动线程
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }

}
