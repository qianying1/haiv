package cn.qhb.haiv.service.common;

import cn.qhb.haiv.model.File;

/**
 * 文件管理
 */
public interface FileManagerService {

    /**
     * 上传文件
     *
     * @param savePath
     * @param originName
     * @param size
     * @param fileSaveName
     * @param fileType
     * @return
     */
    boolean upload(String savePath,String originName, Long size, String fileSaveName, File.FileType fileType);

    /**
     * 按照文件路径与文件名称进行文件删除
     *
     * @param path
     * @param fileSaveName
     * @return
     */
    int deleteByPathAndFileSaveName(String path,String fileSaveName);
}
