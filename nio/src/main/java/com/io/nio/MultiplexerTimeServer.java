package com.io.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Brian in 17:04 2018/4/26
 */
public class MultiplexerTimeServer implements Runnable{

    private Selector selector;
    private ServerSocketChannel servSocketChannel;
    private volatile boolean stop;

    // 初始化多路复用器、绑定监听端口
    public MultiplexerTimeServer(int port) {

        try {
            // 创建多路复用器，并开启
            selector = Selector.open();
            // 打开ServerSocketChannel， 用于监听客户端的连接，它是所有客户端
            // 连接的父管道
            servSocketChannel = ServerSocketChannel.open();
            // 绑定监听端口，设置连接为非阻塞模式
            servSocketChannel.configureBlocking(false);
            servSocketChannel.socket().bind(new InetSocketAddress(port), 1024);

            // 将servSocketChannel 注册到Reactor 线程的多路复用器Selector 上
            // 监听Accept 时间

            servSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();

            System.exit(1);
        }


    }

    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while(!stop){

            try {
                // 多路复用器在线程run 方法的无限循环体内轮询准备就绪的key
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while(it.hasNext()){
                    key = it.next();
                    it.remove();
                    handleInput(key);
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    private void handleInput(SelectionKey key) throws IOException{
        if(key.isValid()){
            // 处理新接入的请求消息
            ServerSocketChannel servSocketChannel = (ServerSocketChannel) key.channel();

            // 多路复用器监听到有新的客户端接入，处理新的接入请求
            // 完成TCP 三次握手
            SocketChannel socketChannel = servSocketChannel.accept();

            // 设置客户端链路为非阻塞模式
            socketChannel.configureBlocking(false);

            // 将新接入的客户端连接注册到Reactor 线程的多路复用器上，监听读操作
            // 用来读取客户端发送的网络消息
            socketChannel.register(selector, SelectionKey.OP_READ);
        }

        if(key.isReadable()){
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);

            // 异步读取客户端请求消息到缓冲区
            int readBytes = socketChannel.read(readBuffer);
            if(readBytes > 0){
                readBuffer.flip();
                byte[] bytes = new byte[readBuffer.remaining()];
                readBuffer.get(bytes);

                String body = new String(bytes, "utf-8");

                String currentTime = new Date(System.currentTimeMillis()).toString();
                doWrite(socketChannel, currentTime);
            }else if(readBytes < 0){
                key.cancel();
                socketChannel.close();
            }
        }
    }

    private void doWrite(SocketChannel channel, String res) throws IOException {
        if(res != null && res.trim().length() > 0){
            byte[] bytes = res.getBytes();

            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();

            channel.write(writeBuffer);
        }
    }

}
