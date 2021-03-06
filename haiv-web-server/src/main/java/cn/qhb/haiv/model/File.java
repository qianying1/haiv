package cn.qhb.haiv.model;

import java.io.Serializable;

/**
 * 文件
 */
public class File extends BaseModel implements Serializable {

    /**
     * 保存路径
     */
    private String savePath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件真实名称
     */
    private String fileRealName;
    /**
     * 文件大小
     */
    private long size;
    /**
     * 文件类型
     */
    private FileType fileType;

    /**
     * 文件类型
     */
    public enum FileType {
        /**
         * 表格
         */
        excel,
        /**
         * word文档
         */
        doc,
        /**
         * 其他
         */
        others;
    }

    public File(String savePath, String fileName, String fileRealName, long size, FileType fileType) {
        this.savePath = savePath;
        this.fileName = fileName;
        this.fileRealName = fileRealName;
        this.size = size;
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getFileRealName() {
        return fileRealName;
    }

    public void setFileRealName(String fileRealName) {
        this.fileRealName = fileRealName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", fileRealName='" + fileRealName + '\'' +
                ", size=" + size +
                ", fileType=" + fileType +
                '}';
    }


}
