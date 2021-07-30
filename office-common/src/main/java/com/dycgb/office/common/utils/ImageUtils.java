package com.dycgb.office.common.utils;

import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @Description 图片获取类
 * @Author myhe
 * @Date 2021/4/27 下午11:08
 */
@Component
public class ImageUtils {

    public void readImage(OutputStream os, String filePath) throws IOException {
        File imageFile = new File(filePath);
        FileInputStream fis = new FileInputStream(imageFile);
        byte[] buffer = new byte[1024];
        while (fis.read(buffer) != -1) {
            os.write(buffer);
        }
        os.flush();
    }
}
