package com.itheima.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageRowBounds;
import com.itheima.dao.IOrdersDao;
import com.itheima.domain.Orders;
import com.itheima.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private IOrdersDao ordersDao;

    @Override
    public List<Orders> findAll() throws Exception {
        return ordersDao.findAll();
    }
    @Override
    public List<Orders> findAll2(int pageNo,int pageSize) throws Exception {
        /*
            pageNo：当前页码
            pageSize：每页显示数量
        * */
        PageHelper.startPage(pageNo,pageSize);
        return ordersDao.findAll();
    }

    @Override
    public Orders findOrderById(String id) throws Exception {
        return ordersDao.findOrderById(id);
    }

}
