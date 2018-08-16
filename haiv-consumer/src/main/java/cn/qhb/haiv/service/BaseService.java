package cn.qhb.haiv.service;

import java.util.List;

/**
 * 基础服务接口
 */
public interface BaseService<T> {

    /**
     * 添加实体
     *
     * @param entity
     * @return
     */
    int addEntity(T entity);

    /**
     * 更新实体
     *
     * @param entity
     * @return
     */
    String updateEntity(T entity);

    /**
     * 删除实体
     *
     * @param entity
     * @return
     */
    int deleteEntity(T entity);

    /**
     * 按照id串进行批量删除
     *
     * @param ids
     * @return
     */
    int batchDeleteByIds(String ids);

    /**
     * 按照id数组进行批量删除
     *
     * @param ids
     * @return
     */
    int batchDeleteByIdA(Long... ids);

    /**
     * 通过id查找实体
     *
     * @param id
     * @return
     */
    T findOneById(Long id);

    /**
     * 通过参数查找实体
     *
     * @param param
     * @return
     */
    T findOneByParam(Object param);

    /**
     * 通过参数分页查找实体列表
     *
     * @param params
     * @return
     */
    List<T> findPageByParams(Object... params);
}
