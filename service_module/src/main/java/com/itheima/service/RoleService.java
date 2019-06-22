package com.itheima.service;

import com.itheima.domain.Permission;
import com.itheima.domain.Role;

import java.util.List;

public interface RoleService {

    public List<Role> findAllRoles() throws Exception;

    public void addRole(Role role) throws Exception;

    List<Permission> getRoleAddiblePermissions(String roleId) throws Exception;

    void addPermissionToRole(String[] ids, String roleId) throws Exception;

    Role findRoleById(String roleId) throws  Exception;
}
