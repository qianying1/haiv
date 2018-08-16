package cn.qhb.haiv.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 文件工具
 */
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 文件上传基本路径
     */
    private String basePath = Constants.FILE_BASE_BATH;

    /**
     * 上传文件
     *
     * @param middlePath   中间地址
     * @param saveFileName 保存文件名
     * @param is           文件输入流
     */
    public String uploadFile(String middlePath, String saveFileName, InputStream is) throws IOException {
        OutputStream os = null;
        String path;
        try {
            if (saveFileName == null || saveFileName.trim().equals("") || is == null || is.available() <= 0) {
                return "";
            }
            String prePath = this.basePath + File.separator + middlePath + File.separator;
            File file = new File(prePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = prePath + saveFileName;
            file = new File(path);
            if (file.exists())
                return "";
            file.createNewFile();
            os = new FileOutputStream(file);
            byte[] reads = new byte[1024];
            while (is.read(reads) != -1) {
                os.write(reads);
            }
        } catch (IOException ioe) {
            logger.error("读写文件出错！", ioe);
            return "";
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
        return path;
    }

    /**
     * 删除指定文件
     *
     * @param middlePath
     * @param saveFileName
     */
    public void deleteFile(String middlePath, String saveFileName) throws Exception {
        String path;
        if (saveFileName == null || saveFileName.trim().equals("")) {
            return;
        }
        String prePath = this.basePath + File.separator + middlePath + File.separator;
        File file = new File(prePath);
        if (!file.exists()) {
            return;
        }
        path = prePath + saveFileName;
        file = new File(path);
        if (file.isDirectory())
            return;
        if (file.exists())
            file.delete();
    }

    /**
     * 按照具体路径进行删除文件操作
     *
     * @param path
     */
    public static void deleteFile(String path) {
        if (path == null || path.trim().equals("")) {
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        file.delete();
    }

    /**
     * 初始化基础路径
     */
    private void initBasePath() {

    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String generateRandomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] retArray = new String[number];
        for (int i = 0; i < number; i++) {
            retArray[i] = generateRandomUUID();
        }
        return retArray;
    }

    /**
     * 判断是否为允许的文档文件类型
     *
     * @param fileName
     * @return
     */
    public static boolean fileAllowedWithDocType(String fileName) {
        for (String ext : Constants.DOC_FILE_ALLOWS) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为允许的表格文件类型
     *
     * @param fileName
     * @return
     */
    public static boolean fileAllowedWithExcelType(String fileName) {
        for (String ext : Constants.EXCEL_FILE_ALLOWS) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取基础路径
     *
     * @param path
     * @return
     */
    public static String getBasePath(String path) {
        if (path == null || path.trim().equals("")) {
            return "";
        }
        return path.substring(0, path.lastIndexOf(File.separator));
    }

    /**
     * 获取具体路径中的文件名称
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        if (path == null || path.trim().equals("")) {
            return "";
        }
        return path.substring(path.lastIndexOf(File.separator) + 1, path.length());
    }

    /**
     * 获取文件的扩展名
     *
     * @param path
     * @return
     */
    public static String getFileExt(String path) {
        String fileName = getFileName(path);
        if (fileName.equals("")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    /**
     * 判断文件长度是否被允许
     *
     * @param size
     */
    public static boolean fileSizeAllowedWithExcelType(Long size) {
        System.out.println("config max file size : " + Constants.EXCEL_FILE_MAX_SIZE);
        return Constants.EXCEL_FILE_MAX_SIZE.doubleValue() > size.doubleValue();
    }

    /**
     * 向响应流中输出文件流
     *
     * @param response 响应句柄
     * @param filePath 文件存放的地址
     * @param fileName 文件输出时的名称
     * @return
     */
    public static boolean outputFile(final HttpServletResponse response, String filePath, String fileName) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        InputStream ins = null;
        OutputStream os = null;
        try {
            ins = new FileInputStream(file);
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName + filePath.substring(filePath.lastIndexOf("."), filePath.length()), "UTF-8") + "\"");
            response.addHeader("Content-Length", "" + ins.available());
            response.setContentType("application/octet-stream;charset=UTF-8");
            os = new BufferedOutputStream(response.getOutputStream());
            byte[] osBytes = new byte[1024];
            while (ins.read(osBytes) != -1) {
                os.write(osBytes);
            }
            os.flush();
            return true;
        } catch (Exception e) {
            logger.error("传输文件流错误！", e);
            return false;
        } finally {
            if (os != null) {
                os.close();
            }
            if (ins != null) {
                ins.close();
            }
        }
    }

    public static void main(String... args) {
        System.out.println(generateRandomUUID());
    }

}
