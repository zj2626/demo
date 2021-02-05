package service.cloud.client3.start.config;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import service.cloud.client3.start.data.Database;
import service.cloud.client3.start.data.MyUser;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private Database database;

    /**
     * 1. 根据用户名查询用户信息+权限, spring底层会判断密码正确性
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = database.getUserInfoByName(username);
        System.out.println("> 用户信息: " + user.getUsername() + " | " + user.getPassword());
        System.out.println("> 用户权限: " + JSON.toJSONString(user.getAuthorities()));
        return user;
    }
}
