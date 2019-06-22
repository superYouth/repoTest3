package com.itheima.service;

import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserInfo> findAllUsers() throws Exception;
    void addUser(UserInfo userInfo) throws Exception;
    UserInfo findUserById(String userId) throws  Exception;

    List<Role> getUserAddibleRole(String userId) throws Exception;

    void addRoleToUser(String[] ids,String userId) throws Exception;
}
