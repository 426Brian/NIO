package com.io.nioBase.dataGram;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * Created by Brian in 23:12 2018/11/26
 */
public class ReceiveDemo {
    public static void main(String[] args) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);

        datagramChannel.bind(new InetSocketAddress(9898));

        Selector selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);


        while (selector.select()> 0){
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

            while(iter.hasNext()){
                SelectionKey skey = iter.next();

                if(skey.isReadable()){
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    datagramChannel.receive(buffer);
                    buffer.flip();

                    System.out.println(new String(buffer.array(), 0, buffer.limit()));
                }

                iter.remove();
            }
        }
    }
}
