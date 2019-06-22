package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Orders;
import com.itheima.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
/*    //不分页
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        List<Orders> orders = ordersService.findAll();
        ModelAndView mv = new ModelAndView();
        mv.addObject("orders",orders);
        mv.setViewName("orders-list");
        return  mv;

    }*/
	//分页
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = false, defaultValue = "1")Integer pageNo,
                                @RequestParam(name = "size", required = false, defaultValue = "4") Integer pageSize) throws Exception {
        List<Orders> orders = ordersService.findAll2(pageNo,pageSize);
        ModelAndView mv = new ModelAndView();
        PageInfo<Orders> pageInfo = new PageInfo<Orders>(orders);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("orders-list");
        return  mv;
    }

    @RequestMapping("/findById.do")
    public ModelAndView findOrderById(String id) throws Exception {
        Orders order = ordersService.findOrderById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("order",order);
        mv.setViewName("orders-show");
        return  mv;
    }
}
