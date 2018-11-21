package com.timo.tools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qinlinsen
 */
public class DataFomatterGUIUtils {

    private static final ThreadLocal<String> map = new ThreadLocal<String>();
    private static final Map<String, String> absolutePathMap = new HashMap<String, String>();
    private static final Map<String, JTextField> textMap = new HashMap<String, JTextField>();
    private static final String SELECT_SOURCE_FILE_DESC = "选择源文件";
    private static final String TARGET_FILE_PATH_DESC = "生成文件的目录";
    private static final String ORGANIZATION_CODE= "请输入机构号";
    private static final String TEXT_FILED= "orgCode";

    public static void main(String[] args) {
        JFrame frame = new JFrame("数据格式转化的小型工具");
        frame.setSize(680, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);

        final JLabel label = new JLabel(ORGANIZATION_CODE);
        label.setBounds(10, 50, 80, 25);
        panel.add(label);
        final JTextField orgCode = new JTextField(20);
        orgCode.setBounds(100, 50, 165, 25);
        orgCode.setText("");
        panel.add(orgCode);
        textMap.put(TEXT_FILED,orgCode);

        placeComponentButton(panel, SELECT_SOURCE_FILE_DESC);

        placeComponentButton(panel, TARGET_FILE_PATH_DESC);
        frame.setVisible(true);
    }

    /**
     * @param panel
     * @param buttonDescription
     * @return 所选择文件的绝对路径
     */
    private static void placeComponentButton(final JPanel panel, final String buttonDescription) {
        final JButton selectPropertiesFileButton = new JButton(buttonDescription);
        selectPropertiesFileButton.setBounds(10, 190, 180, 25);
        panel.add(selectPropertiesFileButton);
        selectPropertiesFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orgCode = textMap.get(TEXT_FILED).getText();
                System.out.println("map.get()="+ orgCode);
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.showDialog(new JLabel(), buttonDescription);
                File file = jfc.getSelectedFile();
                if (file != null && file.isFile()) {
                    String sourceFileAbsolutePath = file.getAbsolutePath();
                    System.out.println("sourceFileAbsolutePath=" + sourceFileAbsolutePath);
                    selectPropertiesFileButton.setText(sourceFileAbsolutePath);
                    absolutePathMap.put(SELECT_SOURCE_FILE_DESC, sourceFileAbsolutePath);
                }
                if (file != null && file.isDirectory()) {
                    String destinationDirectory = file.getAbsolutePath();
                    System.out.println("destinationDirectory=" + destinationDirectory);
                    selectPropertiesFileButton.setText(destinationDirectory);
                    absolutePathMap.put(TARGET_FILE_PATH_DESC, destinationDirectory);
                }
                String sourceFileAbsolutePath = absolutePathMap.get(SELECT_SOURCE_FILE_DESC);
                String destinationDirectory = absolutePathMap.get(TARGET_FILE_PATH_DESC);
                if(sourceFileAbsolutePath!=null && destinationDirectory!=null){
                    if(orgCode.length()>0){
                        FileUtils.generateTargetFile(sourceFileAbsolutePath,destinationDirectory+"//",orgCode);
                        JOptionPane.showMessageDialog(null," 图片生成成功");
                    }
                }
            }
        });
    }
    public static File createFileWhenNotExist(String filePath, String fileName) {
        // 不管是路径还是文件都可以直接new，这里不会报错
        File file = new File(filePath);
        // 判断文件路径是否存在
        if (!file.exists()) {
            System.out.println("文件路径不存在：" + filePath);
            // 如果不存在就创建文件
            file.mkdir();
            System.out.println("创建文件路径成功");
        }

        file = new File(filePath + fileName);
        if (!file.exists()) {
            try {
                System.out.println("文件不存在：" + filePath + fileName);
                file.createNewFile();
                System.out.println("创建文件成功");
            } catch (IOException e) {
                System.out.println("创建文件失败");
                e.printStackTrace();
            }
        }
        return file;
    }
}
