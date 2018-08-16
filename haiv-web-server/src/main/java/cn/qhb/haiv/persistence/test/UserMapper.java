package cn.qhb.haiv.persistence.test;

import cn.qhb.haiv.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository("userMapper")
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    @Transactional
    int insert(User record);


    @Transactional
    List<User> selectAllUser();

//    int insertSelective(User record);

    @Transactional
    @Select("select * from tb_user where user_id=#{userId}")
    User selectByPrimaryKey(Integer userId);

    @Transactional
    @Select("select count(user_id) from t_user")
    int countUser();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}