package com.itheima.service.Impl;

import com.itheima.dao.IRoleDao;
import com.itheima.domain.Permission;
import com.itheima.domain.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private IRoleDao roleDao;
    @Override
    public List<Role> findAllRoles() throws Exception {
        return roleDao.findAllRoles();
    }
    @Transactional
    @Override
    public void addRole(Role role) throws Exception {
        roleDao.save(role);
    }

    @Override
    public List<Permission> getRoleAddiblePermissions(String roleId) throws Exception {
        return roleDao.getRoleAddiblePermissions(roleId);
    }

    @Transactional
    @Override
    public void addPermissionToRole(String[] ids, String roleId) throws Exception {
        for (String permissionId : ids) {
            roleDao.addPermissionToRole(permissionId,roleId);
        }
    }

    @Override
    public Role findRoleById(String roleId) throws Exception {
        return roleDao.findRoleByRoleId(roleId);
    }
}
