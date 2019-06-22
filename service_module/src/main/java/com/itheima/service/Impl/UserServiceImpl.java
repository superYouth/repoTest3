package com.itheima.service.Impl;

import com.itheima.dao.IUserDao;
import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try {
            userInfo = userDao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //处理自己的用户对象封装成UserDetails
        //注：如果如果密码没有配置加密，则密码前需要加上"{noop}"前缀。
//        User user = new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),getAuthorities(userInfo.getRoles()));
        User user = new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getStatus() == 1 ? true : false, true, true, true, getAuthorities(userInfo.getRoles()));
        return user;
    }

    //作用就是返回一个Set集合，集合中装入的是角色描述
    public Set<GrantedAuthority> getAuthorities(List<Role> roles) {
        Set<GrantedAuthority> set = new HashSet<>();
        for (Role role : roles) {
            set.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }
        return set;
    }

    @Override
    public List<UserInfo> findAllUsers() throws Exception {
        return userDao.findAllUsers();
    }

    @Transactional
    @Override
    public void addUser(UserInfo userInfo) throws Exception {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userDao.addUser(userInfo);
    }

    @Override
    public UserInfo findUserById(String userId) throws Exception {

        return userDao.findUserById(userId);
    }

    @Override
    public List<Role> getUserAddibleRole(String userId) throws Exception {
        return userDao.getUserAddibleRoleById(userId);
    }

    @Transactional
    @Override
    public void addRoleToUser(String[] ids, String userId) throws Exception {
        for (String roleId : ids) {

            userDao.addRoleToUser(roleId, userId);
        }
    }

}
