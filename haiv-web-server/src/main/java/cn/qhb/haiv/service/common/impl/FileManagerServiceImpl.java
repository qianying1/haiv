package cn.qhb.haiv.service.common.impl;

import cn.qhb.haiv.model.File;
import cn.qhb.haiv.service.common.FileManagerService;
import cn.qhb.haiv.transaction.common.FileManagerTransaction;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 文件管理
 */
@Service(version = "1.0.0")
public class FileManagerServiceImpl implements FileManagerService {
    private static Logger logger = LoggerFactory.getLogger(FileManagerServiceImpl.class);
    @Resource(name = "fileManagerTransactionImpl")
    private FileManagerTransaction fileManagerTransaction;

    /**
     * 上传文件
     *
     * @param savePath     文件保存路径
     * @param originName   文件原始名称
     * @param size         文件大小
     * @param fileSaveName 文件保存时的名称
     * @param fileType     文件类型
     * @return
     */
    @Override
    public boolean upload(String savePath, String originName, Long size, String fileSaveName, File.FileType fileType) {
        File file = new File(savePath, originName, fileSaveName, size, fileType);
        try {
            int result = fileManagerTransaction.saveFile(file);
            if (result <= 0) {
                return false;
            }
        } catch (Exception sqlE) {
            logger.error("插入文件信息失败！", sqlE);
            return false;
        }
        return true;
    }

    /**
     * 按照文件路径与文件名称进行文件删除
     *
     * @param path
     * @param fileSaveName
     * @return
     */
    public int deleteByPathAndFileSaveName(String path, String fileSaveName) {
        try {
            return fileManagerTransaction.deleteByPathAndFileSaveName(path, fileSaveName);
        } catch (Exception e) {
            logger.error("按照文件保存路径与文件真实名称删除文件信息失败！", e);
            return 0;
        }
    }
}
