package cn.qhb.haiv.transaction.assm.impl;

import cn.qhb.haiv.model.Warning;
import cn.qhb.haiv.persistence.WarningMapper;
import cn.qhb.haiv.transaction.assm.WarningTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class WarningTransactionImpl /*extends MapperHelper<Warning>*/ implements WarningTransaction {
    private static Logger logger = LoggerFactory.getLogger(DeviceTransactionImpl.class);

    @Resource(name = "warningMapper")
    private WarningMapper warningMapper;

    /**
     * 对设备资产进行统计计数
     *
     * @return
     */
    @Transactional(readOnly = true)
    public int countWarning() {
        try {
            return warningMapper.countByParams(null);
        } catch (Exception sqle) {
            logger.error("执行sql语句出现错误！", sqle);
            return 0;
        }
    }

    /**
     * 分页查询设备信息
     *
     * @param start
     * @param offset
     * @return
     */
    @Transactional(readOnly = true)
    public List<Warning> findPage(int start, int offset) throws Exception {
        try {
//            return selectPage(start,offset);
//            return warningMapper.findPage(start,offset);
            return null;
        } catch (Exception e) {
            logger.error("执行分页查询sql出错！", e);
            throw e;
        }
    }

    /**
     * 更新设备信息
     *
     * @param warning
     * @return
     */
    @Transactional
    public int update(Warning warning) throws Exception {
        return 0;
    }

    /**
     * 通过id查找设备信息
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Warning findOneById(Long id) throws Exception {
        return null;
    }

    /**
     * 通过一个参数查找设备信息
     *
     * @param key
     * @return
     */
    @Transactional(readOnly = true)
    public Warning findOneByOneKey(String key, Object val) {
//        SqlFilter<String>
        return null;
    }

}
