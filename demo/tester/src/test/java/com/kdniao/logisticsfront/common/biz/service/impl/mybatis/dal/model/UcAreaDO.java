package com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.model;

public class UcAreaDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uc_area.id
     *
     * @mbg.generated Tue Apr 09 19:34:22 CST 2019
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uc_area.area_code
     *
     * @mbg.generated Tue Apr 09 19:34:22 CST 2019
     */
    private String areaCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uc_area.area_name
     *
     * @mbg.generated Tue Apr 09 19:34:22 CST 2019
     */
    private String areaName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uc_area.level
     *
     * @mbg.generated Tue Apr 09 19:34:22 CST 2019
     */
    private Integer level;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uc_area.id
     *
     * @return the value of uc_area.id
     *
     * @mbg.generated Tue Apr 09 19:34:22 CST 2019
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uc_area.id
     *
     * @param id the value for uc_area.id
     *
     * @mbg.generated Tue Apr 09 19:34:22 CST 2019
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uc_area.area_code
     *
     * @return the value of uc_area.area_code
     *
     * @mbg.generated Tue Apr 09 19:34:22 CST 2019
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uc_area.area_code
     *
     * @param areaCode the value for uc_area.area_code
     *
     * @mbg.generated Tue Apr 09 19:34:22 CST 2019
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uc_area.area_name
     *
     * @return the value of uc_area.area_name
     *
     * @mbg.generated Tue Apr 09 19:34:22 CST 2019
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uc_area.area_name
     *
     * @param areaName the value for uc_area.area_name
     *
     * @mbg.generated Tue Apr 09 19:34:22 CST 2019
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uc_area.level
     *
     * @return the value of uc_area.level
     *
     * @mbg.generated Tue Apr 09 19:34:22 CST 2019
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uc_area.level
     *
     * @param level the value for uc_area.level
     *
     * @mbg.generated Tue Apr 09 19:34:22 CST 2019
     */
    public void setLevel(Integer level) {
        this.level = level;
    }
}