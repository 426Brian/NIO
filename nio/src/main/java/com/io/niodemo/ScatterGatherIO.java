package com.io.niodemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

/**
 * Created by Brian in 17:40 2018/5/3
 */
public class ScatterGatherIO {
    public static void main(String[] args) {
        String data = "Scatting and gathering example";
        gatherBytes(data);
        scatterBytes();
    }


    /**
     * gatherBytes() is used for reading the bytes from
     * the buffers and write it to a file channel.
     * @param data
     */
    private static void gatherBytes(String data) {
        String relativePath = System.getProperty("user.dir");
        // The first buffer is used for holding a random number
        ByteBuffer buffer1 = ByteBuffer.allocate(8);

        // The second buffer is used for holding a data that we want to write
        ByteBuffer buffer2 = ByteBuffer.allocate(400);

        buffer1.asIntBuffer().put(420);
        buffer2.asCharBuffer().put(data);
        GatheringByteChannel gatherer = createChannelInstance(relativePath + "/file_dir/testout.txt", true);
        try {
            // Write the data into the file
            gatherer.write(new ByteBuffer[]{buffer1, buffer2});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileChannel createChannelInstance(String file, boolean isOutput) {
        FileChannel fChannel = null;

        try {
            if (isOutput) {
                fChannel = new FileOutputStream(file).getChannel();
            }else{
                fChannel = new FileInputStream(file).getChannel();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return fChannel;
    }

    /**
     * scatterBytes() is used for reading the bytes from a file channel into
     * a set of buffers.
     */
    private static void scatterBytes() {
        String relativePath = System.getProperty("user.dir");
        // The First Buffer is used for holding a random number
        ByteBuffer buffer1 = ByteBuffer.allocate(8);
        // The Second Buffer is used for holding a data that we want to write
        ByteBuffer buffer2 = ByteBuffer.allocate(400);

        ScatteringByteChannel scatter = createChannelInstance(relativePath+"/file_dir/testout.txt", false);

        try {
            scatter.read(new ByteBuffer[]{buffer1, buffer2});
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read the two buffers seperately
        buffer1.rewind();
        buffer2.rewind();

        int bufferOne = buffer1.asIntBuffer().get();
        String bufferTwo = buffer2.asCharBuffer().toString();

        // Verification of content
        System.out.println(bufferOne);
        System.out.println(bufferTwo);
    }
}
