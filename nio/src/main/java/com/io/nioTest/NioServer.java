package com.io.nioTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Brian in 23:53 2018/11/7
 */
public class NioServer {
    private int flag = 1;
    private int blockSize = 4096;
    private ByteBuffer sendBuffer = ByteBuffer.allocate(blockSize);
    private ByteBuffer receiveBuffer = ByteBuffer.allocate(blockSize);
    private Selector selector;

    public NioServer(int port) throws IOException {
        // 创建服务器端管道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 设置是否阻塞
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        // 绑定服务器端服务端口
        serverSocket.bind(new InetSocketAddress(port));
        // 打开选择器
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("== server start --> "+port);

    }

    // 监听
    public void listen() throws IOException {
        while(true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeys.iterator();
            while(iter.hasNext()){
                SelectionKey selectionKey = iter.next();
                iter.remove();

                // 业务逻辑
                handleKey(selectionKey);
            }
        }
    }

    public void handleKey(SelectionKey selectionKey)throws IOException{
        ServerSocketChannel server;
        SocketChannel client;

        String receiveText;
        String sendText;
        int count = 0;
        if(selectionKey.isAcceptable()){
            server = (ServerSocketChannel) selectionKey.channel();
            client = server.accept();
            client.configureBlocking(false);
            client.register(selector, selectionKey.OP_READ);
        }else if(selectionKey.isReadable()){
            client = (SocketChannel) selectionKey.channel();
            count = client.read(receiveBuffer);

            if(count > 0){
                receiveText = new String(receiveBuffer.array(), 0, count);
                System.out.println("服务器端接收到客户端的信息 --> "+receiveText);
                client.register(selector, selectionKey.OP_WRITE);
            }
        }else if(selectionKey.isWritable()){
            sendBuffer.clear();
            client = (SocketChannel) selectionKey.channel();
            // 发送的数据
            sendText = "msg send to client"+flag++;
            sendBuffer.put(sendText.getBytes());
            sendBuffer.flip();

            client.write(sendBuffer);
            System.out.println("服务器发送给客户端的数据 --> "+sendText);
        }
    }

    public static void main(String[] args) throws IOException{
        int port = 7080;
        NioServer nioServer = new NioServer(port);
        nioServer.listen();
    }
}
