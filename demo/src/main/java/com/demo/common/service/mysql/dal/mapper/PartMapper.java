package com.demo.common.service.mysql.dal.mapper;

import com.demo.common.service.mysql.dal.model.Part;
import com.demo.common.service.mysql.dal.model.PartExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PartMapper {
    /**
     * This method was generated by my.batis Generator.
     * This method corresponds to the database table part
     *
     * @mbggenerated Fri Jun 14 14:33:54 CST 2019
     */
    int countByExample(PartExample example);

    /**
     * This method was generated by my.batis Generator.
     * This method corresponds to the database table part
     *
     * @mbggenerated Fri Jun 14 14:33:54 CST 2019
     */
    int deleteByExample(PartExample example);

    /**
     * This method was generated by my.batis Generator.
     * This method corresponds to the database table part
     *
     * @mbggenerated Fri Jun 14 14:33:54 CST 2019
     */
    int insert(Part record);

    /**
     * This method was generated by my.batis Generator.
     * This method corresponds to the database table part
     *
     * @mbggenerated Fri Jun 14 14:33:54 CST 2019
     */
    int insertSelective(Part record);

    /**
     * This method was generated by my.batis Generator.
     * This method corresponds to the database table part
     *
     * @mbggenerated Fri Jun 14 14:33:54 CST 2019
     */
    List<Part> selectByExample(PartExample example);

    /**
     * This method was generated by my.batis Generator.
     * This method corresponds to the database table part
     *
     * @mbggenerated Fri Jun 14 14:33:54 CST 2019
     */
    int updateByExampleSelective(@Param("record") Part record, @Param("example") PartExample example);

    /**
     * This method was generated by my.batis Generator.
     * This method corresponds to the database table part
     *
     * @mbggenerated Fri Jun 14 14:33:54 CST 2019
     */
    int updateByExample(@Param("record") Part record, @Param("example") PartExample example);
}