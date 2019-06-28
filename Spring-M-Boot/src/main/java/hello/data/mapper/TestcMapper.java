package hello.data.mapper;

import hello.data.model.Testc;
import hello.data.model.TestcExample;
import org.springframework.data.repository.query.Param;

import java.util.List;
//import org.apache.ibatis.annotations.Param;

public interface TestcMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testc
     *
     * @mbggenerated Mon Jun 24 11:50:51 CST 2019
     */
    int countByExample(TestcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testc
     *
     * @mbggenerated Mon Jun 24 11:50:51 CST 2019
     */
    int deleteByExample(TestcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testc
     *
     * @mbggenerated Mon Jun 24 11:50:51 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testc
     *
     * @mbggenerated Mon Jun 24 11:50:51 CST 2019
     */
    int insert(Testc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testc
     *
     * @mbggenerated Mon Jun 24 11:50:51 CST 2019
     */
    int insertSelective(Testc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testc
     *
     * @mbggenerated Mon Jun 24 11:50:51 CST 2019
     */
    List<Testc> selectByExample(TestcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testc
     *
     * @mbggenerated Mon Jun 24 11:50:51 CST 2019
     */
    Testc selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testc
     *
     * @mbggenerated Mon Jun 24 11:50:51 CST 2019
     */
    int updateByExampleSelective(@Param("record") Testc record, @Param("example") TestcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testc
     *
     * @mbggenerated Mon Jun 24 11:50:51 CST 2019
     */
    int updateByExample(@Param("record") Testc record, @Param("example") TestcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testc
     *
     * @mbggenerated Mon Jun 24 11:50:51 CST 2019
     */
    int updateByPrimaryKeySelective(Testc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table testc
     *
     * @mbggenerated Mon Jun 24 11:50:51 CST 2019
     */
    int updateByPrimaryKey(Testc record);
}