package cn.qhb.haiv.service.test.impl;

import cn.qhb.haiv.persistence.test.UserMapper;
import cn.qhb.haiv.model.User;
import com.alibaba.dubbo.config.annotation.Service;
//import com.github.pagehelper.PageHelper;
import cn.qhb.haiv.service.test.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service(version="1.0.0")
public class UserServiceImpl implements UserService {
    @Resource(name = "userMapper")
    private UserMapper userMapper;

    public int addUser(User user) {
        System.out.println("username: " + user.getUserName() + " password: " + user.getPassword());
        try {
            userMapper.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    /**
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    public List<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
//        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectAllUser();
    }

    public User selectByPrimaryKey(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    public int countUser(){
        return userMapper.countUser();
    }
}
