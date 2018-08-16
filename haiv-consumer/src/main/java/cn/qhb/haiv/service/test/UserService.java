package cn.qhb.haiv.service.test;


import cn.qhb.haiv.model.User;

import java.util.List;

public interface UserService {

    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);

    User selectByPrimaryKey(Integer userId);

    int countUser();
}
