package com.io.nio;

/**
 * 客户端通过socket 创建，发送查询时间服务器的指令
 * 读取服务器端的响应并将结果打印出来，随后关闭连接，释放资源
 * Created by Brian in 22:04 2018/4/25
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 9030;

        new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();

    }
}
