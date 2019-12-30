package com.io.nioTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Brian in 19:52 2018/11/8
 */
public class NioClient {
    private int flag = 1;
    private int blockSize = 4096;
    private ByteBuffer sendBuffer = ByteBuffer.allocate(blockSize);
    private ByteBuffer receiveBuffer = ByteBuffer.allocate(blockSize);
    private static final InetSocketAddress Server_Address = new InetSocketAddress("localhost", 7080);

    public void clientService() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        // 打开选择器
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(Server_Address);

        Set<SelectionKey> selectionKeys;
        SelectionKey selectionKey;
        Iterator<SelectionKey> iter;
        SocketChannel client;

        String receiveText;
        String sendText;
        int count = 0;
        while (true) {
            selectionKeys = selector.selectedKeys();
            iter = selectionKeys.iterator();
            while (iter.hasNext()) {
                selectionKey = iter.next();
                if (selectionKey.isConnectable()) {
                    System.out.println("client connect --> ");

                    client = (SocketChannel) selectionKey.channel();
                    if (client.isConnectionPending()) {
                        client.finishConnect();
                        System.out.println("客户端完成连接");
                        sendBuffer.clear();

                        sendBuffer.put("hello server".getBytes());
                        sendBuffer.flip();

                        // 向服务器端发送
                        client.write(sendBuffer);

                    }
                    client.register(selector, SelectionKey.OP_READ);
                }
                if (selectionKey.isReadable()) {
                    client = (SocketChannel) selectionKey.channel();
                    receiveBuffer.clear();
                    count = client.read(receiveBuffer);
                    if (count > 0) {

                    }
                }

            }
        }
    }

    public static void main(String[] args) throws IOException {

    }
}
