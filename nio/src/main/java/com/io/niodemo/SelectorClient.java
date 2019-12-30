package com.io.niodemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by Brian in 20:27 2018/5/3
 */
public class SelectorClient {
    public static void main(String[] args) throws IOException{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        socketChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(256);

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String str = scanner.nextLine();
            buffer.put(str.getBytes());
            buffer.flip();

            socketChannel.write(buffer);
            buffer.clear();
        }
    }
}
