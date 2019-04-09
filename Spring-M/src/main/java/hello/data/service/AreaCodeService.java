/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package hello.data.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import hello.data.mapper.UCAreaDOMapper;
import hello.data.model.UCAreaDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaCodeService {
    private static final Logger logger = LoggerFactory.getLogger(AreaCodeService.class);
    private static String[] provinceUnits = {"省", "自治区", "市", "特别行政区"}; // "新疆生产建设兵团"
    private static String[] cityUnits = {"市", "县", "自治县", "自治州", "地区"};
    private static String[] countyUnits = {"市", "辖区", "区", "县", "自治县"};

    private UCAreaDOMapper areaDOMapper;

    public void setAreaDOMapper(UCAreaDOMapper areaDOMapper) {
        this.areaDOMapper = areaDOMapper;
    }

    /**
     * 通过名称查询code 拼接行政单位 增加查到几率
     *
     * @param name
     * @param level 区域级别  1大陆,2省,3市/自治州,4区/县,5乡/镇/街道
     * @param code
     * @return
     */
    public String queryCodeByName(String name, Integer level, String code) {
        List<String> names = new ArrayList<String>();

        if (StringUtils.isNotEmpty(name)) {
            if (level == 2) {
                for (String provinceUnit : provinceUnits) {
                    if (name.contains(provinceUnit)) {
                        name = name.replace(provinceUnit, "");
                        break;
                    }
                }

                names.add(name);
                for (String provinceUnit : provinceUnits) {
                    names.add(name + provinceUnit);
                }

            } else if (level == 3) {
                for (String cityUnit : cityUnits) {
                    if (name.contains(cityUnit)) {
                        name = name.replace(cityUnit, "");
                        break;
                    }
                }

                names.add(name);
                for (String cityUnit : cityUnits) {
                    names.add(name + cityUnit);
                }

            } else if (level == 4) {
                for (String countyUnit : countyUnits) {
                    if (name.contains(countyUnit)) {
                        name = name.replace(countyUnit, "");
                        break;
                    }
                }

                names.add(name);
                for (String countyUnit : countyUnits) {
                    names.add(name + countyUnit);
                }
            }
        }

        if (StringUtils.isNotEmpty(code)) {
            code = code.replaceAll("0*$", ""); // 去除结尾的0
        }

        return queryCodeByName(names, level, code);
    }

    /**
     * 通过区域名称获取其对应的code,目前仅为优速(UC)使用
     *
     * @param names 区域名称
     * @param level 区域级别  1大陆,2省,3市/自治州,4区/县,5乡/镇/街道
     * @param code  上级区域编码 用来缩小查询范围
     * @return
     */
    public String queryCodeByName(List<String> names, Integer level, String code) {
        UCAreaDO areaDO = new UCAreaDO();
        if (CollectionUtils.isNotEmpty(names)) {
            if (names.size() == 1) {
                areaDO.setAreaName(names.get(0));
            } else {
                areaDO.setAreaNames(names);
            }
        }
        if (null != level) {
            areaDO.setLevel(level);
        }
        if (StringUtils.isNotEmpty(code)) {
            areaDO.setAreaCode(code);
        }

        List<UCAreaDO> areaDOS = null;
        try {
            areaDOS = areaDOMapper.findByEntity(areaDO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (CollectionUtils.isEmpty(areaDOS)) {
            return null;
        }
        return areaDOS.get(0).getAreaCode();
    }

    public Integer removeArea(String code) {
        UCAreaDO areaDO = new UCAreaDO();
        areaDO.setAreaCode(code);

        try {
            return areaDOMapper.deleteUCArea(areaDO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}