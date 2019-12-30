package com.io.nioBase.noBlock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by Brian in 1:48 2018/11/26
 */
public class NoblockServer {
    public static void main(String[] args) throws IOException {
        // 1. 打开网络连接
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2. 设置成非阻塞
        serverSocketChannel.configureBlocking(false);
        // 3. 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(9898));

        // 4. 利用选择器
        Selector selector = Selector.open();

        // 5. 通道注册到选择器
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 6. 轮询式的获取选择器上已经准备就绪的事件
        while (selector.select() > 0) {

            // 7. 获取当前选择器中所有注册的选择键
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while(it.hasNext()){

                // 8. 获取准备就绪的事件
                SelectionKey skey = it.next();

                // 9. 判断具体是什么事件已经就绪
                if(skey.isAcceptable()){

                    // 10. 准备就绪
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    // 11. 切换成非阻塞
                    socketChannel.configureBlocking(false);

                    // 12. 将通道注册到选择器
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else if(skey.isReadable()){

                    // 13. 获取当前选择器上的 '读就绪' 的通道
                    SocketChannel channel = (SocketChannel) skey.channel();

                    // 14. 读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = 0;
                    while((len = channel.read(buffer)) > 0){

                        System.out.println(new String(buffer.array(), 0, len));
                        buffer.clear();
                    }
                }

                // 15. 移除已经处理的选择键
                it.remove();
            }
        }
    }
}
