package com.itheima.dao;

import com.itheima.domain.Permission;
import com.itheima.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IRoleDao {
    //延时加载
    @Select("select * from role where id in(select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(property = "permissions",column = "id",many = @Many(select = "com.itheima.dao.IPermissionDao.findPermissionsByRoleId"))
    })
    public List<Role> findRolesByUserId(String userId) throws Exception;

    @Select("select * from role")
    public List<Role> findAllRoles() throws Exception;

    @Insert("insert into role (roleName,roleDesc) values (#{roleName},#{roleDesc})")
    public void save(Role role) throws Exception;

    @Select("select * from permission where id not in (select permissionId from role_permission where roleId=#{roleId})")
    List<Permission> getRoleAddiblePermissions(String roleId) throws Exception;

    @Insert("insert into role_permission (permissionId,roleId) values (#{permissionId},#{roleId})")
    void addPermissionToRole(@Param("permissionId") String permissionId, @Param("roleId") String roleId) throws Exception;

    @Select("select * from role where id=#{roleId}")
    @Results({
            @Result(property = "permissions",column = "id",many = @Many(select = "com.itheima.dao.IPermissionDao.findPermissionsByRoleId"))
    })
    Role findRoleByRoleId(String roleId) throws  Exception;
}
