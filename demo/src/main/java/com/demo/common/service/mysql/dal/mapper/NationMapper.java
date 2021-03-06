package com.demo.common.service.mysql.dal.mapper;

import com.demo.common.service.mysql.dal.model.Nation;
import com.demo.common.service.mysql.dal.model.NationExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NationMapper {
    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table nation
     *
     * @mbggenerated Fri Jun 14 14:33:10 CST 2019
     */
    int countByExample(NationExample example);

    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table nation
     *
     * @mbggenerated Fri Jun 14 14:33:10 CST 2019
     */
    int deleteByExample(NationExample example);

    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table nation
     *
     * @mbggenerated Fri Jun 14 14:33:10 CST 2019
     */
    int insert(Nation record);

    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table nation
     *
     * @mbggenerated Fri Jun 14 14:33:10 CST 2019
     */
    int insertSelective(Nation record);

    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table nation
     *
     * @mbggenerated Fri Jun 14 14:33:10 CST 2019
     */
    List<Nation> selectByExample(NationExample example);

    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table nation
     *
     * @mbggenerated Fri Jun 14 14:33:10 CST 2019
     */
    int updateByExampleSelective(@Param("record") Nation record, @Param("example") NationExample example);

    /**
     * This method was generated by my.Batis Generator.
     * This method corresponds to the database table nation
     *
     * @mbggenerated Fri Jun 14 14:33:10 CST 2019
     */
    int updateByExample(@Param("record") Nation record, @Param("example") NationExample example);
}