/*
package cn.qhb.haiv.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import cn.qhb.haiv.service.UserDetailService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

*/
/**
 * Created by qianying on 2018/6/10.
 *//*

@Service(version = "1.0.0")
public class UserDetailServiceImpl implements UserDetailService {

    public void mapper(){

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //SUser对应数据库中的用户表，是最终存储用户和密码的表，可自定义
        //本例使用SUser中的email作为用户名:
        SUser user = suserService.findUserByEmail(userName);

        if (user == null) {

            throw new UsernameNotFoundException("UserName " + userName + " not found");

        }

        // SecurityUser实现UserDetails并将SUser的Email映射为username
        SecurityUser securityUser = new SecurityUser(user);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return securityUser;
    }
}
*/
