package cn.qhb.haiv.transaction.common;

import cn.qhb.haiv.model.File;

/**
 * 文件管理事务
 */
public interface FileManagerTransaction {

    /**
     * 保存文件信息
     *
     * @param file
     * @return
     */
    int saveFile(File file) throws Exception;

    /**
     * 按照文件路径与文件名称进行文件删除
     *
     * @param path
     * @param fileSaveName
     * @return
     */
    int deleteByPathAndFileSaveName(String path,String fileSaveName) throws Exception;
}
