package com.itheima.service.Impl;

import com.itheima.dao.IPermissionDao;
import com.itheima.domain.Permission;
import com.itheima.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private IPermissionDao permissionDao;

    @Override
    public List<Permission> findAllPermissions() throws Exception {
        return permissionDao.findAll();
    }
    @Transactional
    @Override
    public void addPermission(Permission permission) throws Exception {
        permissionDao.save(permission);
    }
}
