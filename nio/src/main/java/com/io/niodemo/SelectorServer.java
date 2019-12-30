package com.io.niodemo;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Brian in 19:55 2018/5/3
 */
public class SelectorServer {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        System.out.println("selector is open for making connection: " + selector.isOpen());

        ServerSocketChannel svSocketChannel = ServerSocketChannel.open();
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 8080);
        svSocketChannel.bind(hostAddress);
        svSocketChannel.configureBlocking(false);
        int ops = svSocketChannel.validOps();

        SelectionKey selectKy = svSocketChannel.register(selector, ops, null);

        while (true) {
            System.out.println("waiting for the selector operation ...");

            int noOfKeys = selector.select();

            System.out.println("The number of selected keys are: " + noOfKeys);

            Set selectedKys = selector.selectedKeys();
            Iterator itr = selectedKys.iterator();

            while (itr.hasNext()) {
                SelectionKey ky = (SelectionKey) itr.next();

                if (ky.isAcceptable()) {
                    SocketChannel socketChannel = svSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);

                    System.out.println("The new connetion is accepted from the client: " + socketChannel);
                } else if (ky.isReadable()) {
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    SocketChannel socketChannel = (SocketChannel) ky.channel();
                    socketChannel.read(buffer);
                    String output = new String(buffer.array()).trim();

                    System.out.println("the message from client is: "+output);
                    if("Bye Bye".equals(output)){
                        socketChannel.close();
                        System.out.println("The client messages are complete; close the session");
                    }
                }

                itr.remove();
            }
        }

    }

}
