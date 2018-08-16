package cn.qhb.haiv.transaction.common.impl;

import cn.qhb.haiv.model.File;
import cn.qhb.haiv.persistence.FileMapper;
import cn.qhb.haiv.transaction.common.FileManagerTransaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 文件管理事务
 */
@Repository
public class FileManagerTransactionImpl implements FileManagerTransaction {

    @Resource(name = "fileMapper")
    private FileMapper fileMapper;

    /**
     * 添加文件信息
     *
     * @return
     */
    @Override
    @Transactional
    public int saveFile(File file) throws Exception {
        return fileMapper.insertFile(file);
    }

    /**
     * 按照文件路径与文件名称进行文件删除
     *
     * @param path
     * @param fileSaveName
     * @return
     */
    @Transactional
    public int deleteByPathAndFileSaveName(String path,String fileSaveName) throws Exception{
        return fileMapper.deleteByPathAndFileSaveName(path,fileSaveName);
    }
}
