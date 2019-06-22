package com.itheima.controller;

import com.itheima.domain.Permission;
import com.itheima.domain.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAllRoles() throws Exception {
        List<Role> roles = roleService.findAllRoles();
        ModelAndView mv = new ModelAndView();
        mv.addObject("roleList",roles);
        mv.setViewName("role-list");
        return mv;
    }
    @RequestMapping("/save.do")
    public String addRole(Role role) throws Exception {
        roleService.addRole(role);
        return "redirect:/role/findAll.do";
    }
    @RequestMapping("/findById.do")
    public ModelAndView findRoleDetails(@RequestParam("id") String roleId) throws Exception {

        Role role = roleService.findRoleById(roleId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("role",role);
        mv.setViewName("role-show");
        return mv;
    }

    @RequestMapping("/findRoleAddiblePermissions.do")
    public ModelAndView  getRoleAddiblePermissions(@RequestParam("id") String roleId) throws Exception{
        List<Permission> permissions = roleService.getRoleAddiblePermissions(roleId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("permissionList",permissions);
        Role role = new Role();
        role.setId(roleId);
        mv.addObject("role",role);
        mv.setViewName("role-permission-add");
        return mv;
    }
    @RequestMapping("/addPermissionToRole.do")
    public String addRoleToUser(@RequestParam("roleId") String roleId,
                                @RequestParam("ids") String[] ids) throws Exception {
        roleService.addPermissionToRole(ids,roleId);
        return "redirect:/role/findAll.do";
    }
}
