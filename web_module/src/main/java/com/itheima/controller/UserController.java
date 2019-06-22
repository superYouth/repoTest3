package com.itheima.controller;

import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RolesAllowed("ROLE_USER")
//    @PreAuthorize()
    @RequestMapping("/findAll.do")
    public ModelAndView findAllUsers() throws Exception {
        List<UserInfo> users = userService.findAllUsers();
        ModelAndView mv = new ModelAndView();
        mv.addObject("users",users);
        mv.setViewName("user-list");
        return mv;
    }

    @RequestMapping("/save.do")
    public String addUser(UserInfo userInfo) throws Exception {
        userService.addUser(userInfo);
        return "redirect:/user/findAll.do";
    }
    @RequestMapping("/findById.do")
    public ModelAndView findUserDetails(String id) throws Exception {

        UserInfo userInfo = userService.findUserById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",userInfo);
        mv.setViewName("user-show");
        return mv;
    }
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView  getUserAddibleRole(@RequestParam("id") String userId) throws Exception{
        List<Role> roles = userService.getUserAddibleRole(userId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("roleList",roles);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        mv.addObject("user",userInfo);
        mv.setViewName("user-role-add");
        return mv;
    }

    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam("userId") String userId,
                                @RequestParam("ids") String[] ids) throws Exception {
        userService.addRoleToUser(ids,userId);
        return "redirect:/user/findAll.do";
    }
}
