package com.timo.tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Abraham Qin
 * @since 2018/11/19
 */
public class FileUtils {
    private static final String DESTINATION_SEPARATOR = "-";
    public static final AtomicInteger workCode = new AtomicInteger(0);
    public static void generateTargetFile(String sourceFileAbsolutePath, String destinationPath, String orgCode) {
        Assert.notNull(destinationPath, "目标路径不能为空");
        if (destinationPath.endsWith("\\") || destinationPath.endsWith("/")) {
            System.out.println("destination path's format is correct");
        } else {
            throw new IllegalArgumentException("destination path must ends with '\\' or '/'");
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFileAbsolutePath), "UTF-8"));
            String sourceDataStr = "";
            while ((sourceDataStr = in.readLine()) != null) {
                if ((sourceDataStr.length() > 0)) {
                    try {
                        process(sourceDataStr, destinationPath, orgCode);
                    } catch (Exception e) {
                        System.out.println("process is encounter an exception :"+e);
                    }
                }
            }
            System.out.println("图片生成完成");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void process(String sourceStr, String destPath, String orgCode) {
        String replacedSourceStr = sourceStr.replace("|!", ",");
        String[] sourceArray = replacedSourceStr.split(",");
        //证件号：
        String identityCardNumber = sourceArray[0];
        //姓名：
        String userName = sourceArray[1];
        //星级：
        String vipLevel = sourceArray[2];
        //工号：
        //这是邮箱的格式：
        //String imageName = identityCardNumber + DESTINATION_SEPARATOR + userName + DESTINATION_SEPARATOR + vipLevel + ".jpg";
        //这个是模板配置的格式：工号用证件号代替
        //String imageName = userName+ DESTINATION_SEPARATOR + identityCardNumber + DESTINATION_SEPARATOR + orgCode + ".jpg";
        //这个是模板配置的格式：工号采取递增的方式：
        String imageName = userName+ DESTINATION_SEPARATOR + workCode.getAndIncrement() + DESTINATION_SEPARATOR + orgCode + ".jpg";
        Base64Utils.generateImage(sourceArray[3], destPath + imageName);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 从键盘接收数据
        System.out.println("请输入源文件所在的绝对路径");
        String sourceFileAbsolutePath = scanner.nextLine();
        System.out.println("请输入图片所在的目录");
        String destinationPath = scanner.nextLine();
        System.out.println("请输入机构号");
        String orgCode = scanner.nextLine();
        generateTargetFile(sourceFileAbsolutePath, destinationPath, orgCode);
    }
}
