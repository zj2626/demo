package com.demo.common.service.mysql.dal.model;

public class Region {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.r_regionkey
     *
     * @mbggenerated Fri Jun 14 14:34:21 CST 2019
     */
    private Integer rRegionkey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.r_name
     *
     * @mbggenerated Fri Jun 14 14:34:21 CST 2019
     */
    private String rName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.r_comment
     *
     * @mbggenerated Fri Jun 14 14:34:21 CST 2019
     */
    private String rComment;

    /**
     * This method was generated by my.Batis Generator.
     * This method returns the value of the database column region.r_regionkey
     *
     * @return the value of region.r_regionkey
     *
     * @mbggenerated Fri Jun 14 14:34:21 CST 2019
     */
    public Integer getrRegionkey() {
        return rRegionkey;
    }

    /**
     * This method was generated by my.Batis Generator.
     * This method sets the value of the database column region.r_regionkey
     *
     * @param rRegionkey the value for region.r_regionkey
     *
     * @mbggenerated Fri Jun 14 14:34:21 CST 2019
     */
    public void setrRegionkey(Integer rRegionkey) {
        this.rRegionkey = rRegionkey;
    }

    /**
     * This method was generated by my.Batis Generator.
     * This method returns the value of the database column region.r_name
     *
     * @return the value of region.r_name
     *
     * @mbggenerated Fri Jun 14 14:34:21 CST 2019
     */
    public String getrName() {
        return rName;
    }

    /**
     * This method was generated by my.Batis Generator.
     * This method sets the value of the database column region.r_name
     *
     * @param rName the value for region.r_name
     *
     * @mbggenerated Fri Jun 14 14:34:21 CST 2019
     */
    public void setrName(String rName) {
        this.rName = rName == null ? null : rName.trim();
    }

    /**
     * This method was generated by my.Batis Generator.
     * This method returns the value of the database column region.r_comment
     *
     * @return the value of region.r_comment
     *
     * @mbggenerated Fri Jun 14 14:34:21 CST 2019
     */
    public String getrComment() {
        return rComment;
    }

    /**
     * This method was generated by my.Batis Generator.
     * This method sets the value of the database column region.r_comment
     *
     * @param rComment the value for region.r_comment
     *
     * @mbggenerated Fri Jun 14 14:34:21 CST 2019
     */
    public void setrComment(String rComment) {
        this.rComment = rComment == null ? null : rComment.trim();
    }
}