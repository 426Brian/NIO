package com.io.nioBase.dataGram;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Created by Brian in 23:12 2018/11/26
 */
public class SendDemo {
    public static void main(String[] args) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String str = scanner.next();
            buffer.put((LocalDateTime.now().toString()+" --> "+str).getBytes());
            buffer.flip();
            datagramChannel.send(buffer, new InetSocketAddress("localhost", 9898));
            buffer.clear();
        }

        datagramChannel.close();
    }
}
