package com.timo.tools;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @author Abraham Qin
 * @since 2018/11/16
 */
public class Base64Utils {
    /**
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     * @param imgStr base64编码字符串
     * @param path 图片路径-具体到文件
     * @return
     */
    public static boolean generateImage(String imgStr,String path){
      Assert.notNull(imgStr,"imgStr cannot be null");
      Assert.notNull(imgStr,"path cannot be null");
        try {
            //解密
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;i++){
                if(b[i]<0){
                    b[i]+=256;
                }
            }
            FileOutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
            // 处理数据
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据图片地址转成base64字符串
     * @param imageFile
     * @return
     */
    public static String getImageStr(String imageFile){
        InputStream inputStream=null;
        byte[] data=null;
        try {
            inputStream = new FileInputStream(imageFile);
            data=new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static void main(String[] args) {
        try {
            String str="320101198411201234|!张三|!03|!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";
            String replace = str.replace("|!", ",");
            String[] split = replace.split(",");
            generateImage(split[3],"C:\\Users\\yckj0911\\Desktop\\1.jpg");
            for(String s:split){
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
