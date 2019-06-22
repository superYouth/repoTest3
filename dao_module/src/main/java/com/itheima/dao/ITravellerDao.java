package com.itheima.dao;

import com.itheima.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ITravellerDao {

    @Select("select * from traveller where id in (select TRAVELLERID from ORDER_TRAVELLER where orderId=#{id})")
    List<Traveller> findTravellersById(String id) throws Exception;
}
