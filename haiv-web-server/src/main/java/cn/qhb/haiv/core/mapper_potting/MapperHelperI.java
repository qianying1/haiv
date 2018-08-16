package cn.qhb.haiv.core.mapper_potting;

public interface MapperHelperI<T> {

    int insert(T entity) throws Exception;

    int update(T entity) throws Exception;
}
