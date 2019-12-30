package com.io.niodemo.fileUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by Brian in 19:28 2018/5/3
 */
public class FileUtil {
    /**
     * 此方法用于确保file是一定存在的，不存在就新建
     * type:"directory" 目录， "file" 文件
     * @param file
     * @param type
     * @throws IOException
     */
    public static void createFile(File file, String type) throws IOException {
        if(!file.exists()){
            if("file".equalsIgnoreCase(type)){
                createFile(file.getParentFile(), "directory");
                file.createNewFile();
            }else{
                file.mkdirs();
            }
        }
    }
}
