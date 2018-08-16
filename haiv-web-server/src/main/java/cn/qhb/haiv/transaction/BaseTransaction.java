package cn.qhb.haiv.transaction;

/**
 * 基础服务接口
 */
public interface BaseTransaction<T> {

    /**
     * 通过主键查找一个实体
     *
     * @return
     */
    T findOneById(Long id) throws Exception;

    /**
     * 通过一个key关键字查找一个实体
     *
     * @param key
     * @return
     */
    T findOneByOneKey(String key,Object val);

    /**
     * 更新实体
     *
     * @param entity
     * @return
     */
    int update(T entity) throws Exception;
}
