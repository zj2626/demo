package com.kdniao.logisticsfront.common.biz.service.impl.mysql.dal.mapper;

import com.kdniao.logisticsfront.common.biz.service.impl.mysql.dal.model.Customer;
import com.kdniao.logisticsfront.common.biz.service.impl.mysql.dal.model.CustomerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbggenerated Fri Jun 14 14:31:45 CST 2019
     */
    int countByExample(CustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbggenerated Fri Jun 14 14:31:45 CST 2019
     */
    int deleteByExample(CustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbggenerated Fri Jun 14 14:31:45 CST 2019
     */
    int insert(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbggenerated Fri Jun 14 14:31:45 CST 2019
     */
    int insertSelective(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbggenerated Fri Jun 14 14:31:45 CST 2019
     */
    List<Customer> selectByExample(CustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbggenerated Fri Jun 14 14:31:45 CST 2019
     */
    int updateByExampleSelective(@Param("record") Customer record, @Param("example") CustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbggenerated Fri Jun 14 14:31:45 CST 2019
     */
    int updateByExample(@Param("record") Customer record, @Param("example") CustomerExample example);
}