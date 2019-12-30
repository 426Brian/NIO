package com.io.nioBase.noBlock;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Created by Brian in 1:48 2018/11/26
 */
public class NoblockClient {
    public static void main(String[] args) throws IOException {
        // 1.打开网络连接
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9898));
        // 2.设置成非阻塞
        socketChannel.configureBlocking(false);
        // 3. 缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 4. 发送当前时间
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.next();
            System.out.println("str == " + str);
            buffer.put((LocalDateTime.now().toString() + " --> " + str).getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

        // 5. 关闭通道
        socketChannel.close();
    }


}
