package com.itheima.service;

import com.github.pagehelper.PageRowBounds;
import com.itheima.domain.Orders;

import java.util.List;

public interface OrdersService {

    List<Orders> findAll() throws Exception;
    List<Orders> findAll2(int pageNo,int pageSize) throws Exception;

    Orders findOrderById(String id) throws Exception;
}
