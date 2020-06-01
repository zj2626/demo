package com.demo.common.service.mysql.dal.mapper;

import com.demo.common.service.mysql.dal.model.Orders;
import com.demo.common.service.mysql.dal.model.OrdersExample;

import java.util.List;

//import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

//@CacheNamespace // 开启mybatis二级缓存
public interface OrdersMapper {
    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jun 14 14:33:40 CST 2019
     */
    int countByExample(OrdersExample example);

    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jun 14 14:33:40 CST 2019
     */
    int deleteByExample(OrdersExample example);

    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jun 14 14:33:40 CST 2019
     */
    int insert(Orders record);

    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jun 14 14:33:40 CST 2019
     */
    int insertSelective(Orders record);

    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jun 14 14:33:40 CST 2019
     */
    List<Orders> selectByExample(OrdersExample example);

    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jun 14 14:33:40 CST 2019
     */
    int updateByExampleSelective(@Param("record") Orders record, @Param("example") OrdersExample example);

    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table orders
     *
     * @mbggenerated Fri Jun 14 14:33:40 CST 2019
     */
    int updateByExample(@Param("record") Orders record, @Param("example") OrdersExample example);
}