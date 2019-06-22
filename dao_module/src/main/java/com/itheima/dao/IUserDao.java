package com.itheima.dao;

import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IUserDao {

    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "email",column = "email"),
            @Result(property = "phoneNum",column = "phonenum"),
            @Result(property = "status",column = "status"),
            @Result(property = "roles",column = "id",many = @Many(select = "com.itheima.dao.IRoleDao.findRolesByUserId"))
    })
    public UserInfo findByUsername(String username) throws Exception;

    @Select("select * from users")
    public List<UserInfo> findAllUsers() throws  Exception;

    @Insert("insert into users (email,username,password,phonenum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
    public void addUser(UserInfo userInfo) throws Exception;

    @Select("select * from users where id=#{userId}")
    @Results({
            @Result(property = "roles",column = "id",many = @Many(select ="com.itheima.dao.IRoleDao.findRolesByUserId"))
    })
    public UserInfo findUserById(String userId) throws Exception;

    @Select("select * from role where id not in (select roleId from users_role where userId=#{userId})")
    List<Role> getUserAddibleRoleById(String userId) throws Exception;

    @Insert("insert into users_role (roleId,userId) values(#{roleId},#{userId})")
    void addRoleToUser(@Param("roleId") String roleId, @Param("userId") String userId) throws Exception;
}