package cn.qhb.haiv.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.*;
import java.util.Properties;

/**
 * 常量池
 */
@Configuration
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties
public class Constants {
    private static Logger logger = LoggerFactory.getLogger(Constants.class);
    /**
     * 配置的属性
     */
    private static Properties properties;

    /**
     * 查询成功返回字符串
     */
    public static String QUERY_SUCCESS = "查询成功！";
    /**
     * 查询失败返回
     */
    public static String QUERY_FAIL = "查询失败！";
    /**
     * 允许的文件后缀类型
     */
    public static String[] DOC_FILE_ALLOWS = {".doc", ".docx"};

    /**
     * 允许的表格文件后缀类型
     */
    public static String[] EXCEL_FILE_ALLOWS = {".xls", ".xlsx"};
    /**
     * 允许文件上传的长度
     */
    public static Long EXCEL_FILE_MAX_SIZE;
    /**
     * 文件上传基本路径
     */
    public static String FILE_BASE_BATH = "f://upload";
    /**
     * 设备信息模板导入字段
     */
    public static String[] DEVICE_OUTPUT_TEMPLATE_FIELDS_EN = {"id"};
    /**
     * 设备信息模板导出字段
     */
    public static String[] DEVICE_OUTPUT_TEMPLATE_FIELDS_CN = {"id"};
    /**
     * 设备信息数据导入字段
     */
    public static String[] DEVICE_OUTPUT_DATA_FIELDS_EN = {"id"};
    /**
     * 设备信息数据导出字段
     */
    public static String[] DEVICE_OUTPUT_DATA_FIELDS_CN = {"id"};
    /**
     * 导入的数据数据开始行
     */
    public static int INPUT_DATA_ROW_START = -1;
    /**
     * 导入的数据数据开始列
     */
    public static int INPUT_DATA_COLUMN_START = -1;
    /**
     * 导出文件所存放的位置
     */
    public static String EXCEL_EXPORT_FILES_PATH;

    /**
     * 加载系统常量属性值
     */
    private static void loadSysProps() {
        QUERY_SUCCESS = properties.getProperty("custom.QUERY_SUCCESS");
        if (QUERY_SUCCESS == null)
            QUERY_SUCCESS = "查询成功！";
        QUERY_FAIL = properties.getProperty("custom.QUERY_FAIL");
        if (QUERY_FAIL == null)
            QUERY_FAIL = "查询失败！";
        DOC_FILE_ALLOWS = properties.getProperty("custom.DOC_FILE_ALLOWS").split(",");
        if (DOC_FILE_ALLOWS == null || DOC_FILE_ALLOWS.length <= 0) {
            DOC_FILE_ALLOWS = new String[2];
            DOC_FILE_ALLOWS[0] = ".doc";
            DOC_FILE_ALLOWS[1] = ".docx";
        }
        EXCEL_FILE_ALLOWS = properties.getProperty("custom.EXCEL_FILE_ALLOWS").split(",");
        if (EXCEL_FILE_ALLOWS == null || EXCEL_FILE_ALLOWS.length <= 0) {
            EXCEL_FILE_ALLOWS = new String[2];
            EXCEL_FILE_ALLOWS[0] = ".xls";
            EXCEL_FILE_ALLOWS[1] = ".xlsx";
        }
        EXCEL_EXPORT_FILES_PATH = properties.getProperty("custom.EXCEL_EXPORT_FILES_PATH");
        String max_size = properties.getProperty("custom.EXCEL_FILE_MAX_SIZE");
        if (max_size != null && !max_size.trim().equals("")) {
            EXCEL_FILE_MAX_SIZE = Long.valueOf(1);
            if (max_size.indexOf("*") != -1) {
                for (String subNum : max_size.split("\\*")) {
                    EXCEL_FILE_MAX_SIZE = EXCEL_FILE_MAX_SIZE * Long.valueOf(subNum);
                }
            } else {
                EXCEL_FILE_MAX_SIZE = Long.valueOf(max_size);
            }
        } else {
            EXCEL_FILE_MAX_SIZE = new Long(0);
        }
        FILE_BASE_BATH = properties.getProperty("custom.FILE_BASE_BATH");
        if (FILE_BASE_BATH == null)
            FILE_BASE_BATH = "D:/uploads";
        String fields = properties.getProperty("custom.DEVICE_OUTPUT_TEMPLATE_FIELDS_EN");
        if (fields != null && !fields.trim().equals("")) {
            DEVICE_OUTPUT_TEMPLATE_FIELDS_EN = fields.split(",");
        }
        fields = properties.getProperty("custom.DEVICE_OUTPUT_TEMPLATE_FIELDS_CN");
        if (fields != null && !fields.trim().equals("")) {
            DEVICE_OUTPUT_TEMPLATE_FIELDS_CN = fields.split(",");
        }
        fields = properties.getProperty("custom.DEVICE_OUTPUT_DATA_FIELDS_EN");
        if (fields != null && !fields.trim().equals("")) {
            DEVICE_OUTPUT_DATA_FIELDS_EN = fields.split(",");
        }
        fields = properties.getProperty("custom.DEVICE_OUTPUT_DATA_FIELDS_CN");
        if (fields != null && !fields.trim().equals("")) {
            DEVICE_OUTPUT_DATA_FIELDS_CN = fields.split(",");
        }
        INPUT_DATA_ROW_START = Integer.valueOf(properties.getProperty("custom.INPUT_DATA_ROW_START"));
        if (INPUT_DATA_ROW_START < 0) {
            INPUT_DATA_ROW_START = 2;
        }
        INPUT_DATA_COLUMN_START = Integer.valueOf(properties.getProperty("custom.INPUT_DATA_COLUMN_START"));
        if (INPUT_DATA_COLUMN_START < 0) {
            INPUT_DATA_COLUMN_START = 2;
        }
    }

    /**
     * 加载属性文件
     */
    private static void loadPropsFile() throws Exception {
        logger.info("loading properties file >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        InputStream inps = null;
        InputStreamReader ipsr = null;
        try {
            try {
                String configLocation = System.getProperty("user.dir") + File.separator + "config" + File.separator + "application.properties";
                logger.info("正在读取配置文件： " + configLocation + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                inps = new FileInputStream(new File(configLocation));
            } catch (FileNotFoundException fnfe) {
                logger.info("外置配置文件读取失败！");
                inps = Constants.class.getResourceAsStream("/application.properties");//new FileInputStream(prof);
            }
            ipsr = new InputStreamReader(inps, "UTF-8");
            properties = new Properties();
            properties.load(ipsr);
        } catch (FileNotFoundException fne) {
            logger.error("读取属性文件失败！", fne);
            return;
        } catch (IOException ioe) {
            logger.error("获取文件输入流失败！", ioe);
            return;
        } finally {
            if (ipsr != null) {
                ipsr.close();
            }
            if (inps != null) {
                inps.close();
            }
        }
    }

    /**
     * 执行文件装载以及系统属性装载代码块
     */
    static {
        try {
            loadPropsFile();
            loadSysProps();
        } catch (Exception e) {
            logger.error("加载配置信息错误！", e);
        }
    }

    public static void main(String... args) {
        System.out.println("QUERY_SUCCESS: ====" + Constants.QUERY_SUCCESS);
        System.out.println("QUERY_FAIL: =============" + Constants.QUERY_FAIL);
    }
}
