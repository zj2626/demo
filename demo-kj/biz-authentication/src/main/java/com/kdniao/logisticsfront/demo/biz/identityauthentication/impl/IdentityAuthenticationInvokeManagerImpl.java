/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.demo.biz.identityauthentication.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.kdniao.demo.common.util.Print;
import com.kdniao.logisticsfront.biz.shared.authentication.impl.AbstractIdentityAuthenticationManagerImpl;
import com.kdniao.logisticsfront.common.util.encrpt.EncryptUtils;
import com.kdniao.logisticsfront.common.util.http.ExterfaceInvokeHttpSender;
import com.kdniao.logisticsfront.demo.core.model.message.IdentityAddresses;
import com.kdniao.logisticsfront.demo.core.model.message.IdentityAuthenticationRequest;
import com.kdniao.logisticsfront.demo.core.model.message.IdentityAuthenticationResponse;
import com.kdniao.logisticsfront.demo.core.model.message.IdentityContactp;
import com.kdniao.logisticsfront.demo.core.service.converter.IdentityAuthenticationRequestFactory;
import com.kdniao.logisticsgw.common.service.model.authentication.AuthenFeedback;
import com.kdniao.logisticsgw.common.service.order.IdentityAuthenticationOrder;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangj
 * @version $Id: IdentityAuthenticationInvokeManagerImpl.java, v 0.1 2018/12/26 15:16 zhangj Exp $
 */
public class IdentityAuthenticationInvokeManagerImpl extends AbstractIdentityAuthenticationManagerImpl {
    private static final Logger logger = LoggerFactory.getLogger(IdentityAuthenticationInvokeManagerImpl.class);
    private static final String ENCODE = "UTF-8";
    private static IdentityAuthenticationRequestFactory requestFactory = new IdentityAuthenticationRequestFactory();
    private static ThreadLocal<ExterfaceInvokeHttpSender> httpThreadLocal = new ThreadLocal<ExterfaceInvokeHttpSender>();
    private ExterfaceInvokeHttpSender identityAuthenticationHttpSender;

    public void setIdentityAuthenticationHttpSender(ExterfaceInvokeHttpSender identityAuthenticationHttpSender) {
        this.identityAuthenticationHttpSender = identityAuthenticationHttpSender;
    }

    public static void main(String[] args) throws Exception {
        ExterfaceInvokeHttpSender identityAuthenticationHttpSender = new ExterfaceInvokeHttpSender();
        identityAuthenticationHttpSender.afterPropertiesSet();

        String data = "{\"address\":\"保税区金花路29号华宝一号大厦A601\"," +
                "\"addressType\":\"2\"," +
                "\"authenticationType\":\"0\"," +
                "\"city\":\"深圳市\"," +
                "\"cityCode\":\"440300\"," +
                "\"collectMethod\":\"40\"," +
                "\"county\":\"福田区\"," +
                "\"countyCode\":\"440304\"," +
                "\"encrypt\":\"UC\"," +
                "\"eytIv\":\"\"," +
                "\"eytKey\":\"\"," +
                "\"format\":\"json\"," +
                "\"gender\":\"1\"," +
                "\"interfaceCode\":\"1007\"," +
                "\"mobile\":\"13259615884\"," +
                "\"name\":\"柏冬梅\"," +
                "\"numberOfId\":\"441423198805150037\"," +
                "\"partnerId\":\"UC\"," +
                "\"phone\":\"64958365\"," +
                "\"province\":\"广东省\"," +
                "\"provinceCode\":\"440000\"," +
                "\"secretKey\":\"uCrePJQRmIaYmeRF\"," +
                "\"shipperCode\":\"DEMO\"," +
                "\"typeOfId\":\"01\"," +
                "\"typeOfUser\":\"1\"," +
                "\"url\":\"http://uop.uc56.com/api/authentication.action\"," +
                "\"version\":\"1.0\"}";

        IdentityAuthenticationOrder order = JSON.parseObject(data, IdentityAuthenticationOrder.class);

        IdentityAuthenticationInvokeManagerImpl identityAuthenticationInvokeManager = new IdentityAuthenticationInvokeManagerImpl();
        identityAuthenticationInvokeManager.setIdentityAuthenticationHttpSender(identityAuthenticationHttpSender);
        AuthenFeedback result = identityAuthenticationInvokeManager.doInvoke(order);
        System.out.println(JSON.toJSONString(result));
    }

    /**
     * UC使用的区域编码(Code),使用的是全国统一的编码,存放于[数据库:kdniao_infra,表:uc_area]中
     *
     * @param order
     * @return
     */
    @Override
    public AuthenFeedback doInvoke(IdentityAuthenticationOrder order) {
        Print.out("实名制demo ", order.getName());

        AuthenFeedback authenFeedback = new AuthenFeedback();
        authenFeedback.setSuccess(true);
        authenFeedback.setResultCode("105");

        IdentityAuthenticationRequest request = requestFactory.create(order);
        // 检查必要条件
        checkData(authenFeedback, request);
        if (!authenFeedback.isSuccess()) return authenFeedback;
        authenFeedback.setSuccess(false);

        Map<String, String> paramMap = new HashMap<String, String>();
        String data = JSON.toJSONString(request);
        paramMap.put("method", StringUtils.isEmpty(order.getMethod()) ? "createrealname" : order.getMethod());
        paramMap.put("partnerid", order.getPartnerId());
        paramMap.put("timestamp", String.valueOf(new Date().getTime() / 1000));
        paramMap.put("format", StringUtils.isEmpty(order.getFormat()) ? "json" : order.getFormat());
        paramMap.put("encrypt", StringUtils.isEmpty(order.getEncrypt()) ? "url" : order.getEncrypt());
        paramMap.put("version", StringUtils.isEmpty(order.getVersion()) ? "1.0" : order.getVersion());
        paramMap.put("data", data);
        String sign = EncryptUtils.encryptSign(paramMap, order.getSecretKey());
        paramMap.put("sign", sign);
        paramMap.remove("data");
        print("请求实名参数", data);

        IdentityAuthenticationResponse response;
        try {
            httpThreadLocal.set(identityAuthenticationHttpSender);
            ExterfaceInvokeHttpSender httpSender = httpThreadLocal.get();
            httpSender.setContentType("application/json;charset=UTF-8");
            httpSender.setHostname(order.getUrl());
            String responseString = httpSender.send(paramMap, URLEncoder.encode(data, ENCODE), HttpPost.METHOD_NAME);
            print("实名请求返回", responseString);
            response = JSON.parseObject(responseString, IdentityAuthenticationResponse.class);
        } catch (JSONException e) {
            logger.error("反序列化响应时出错", e);
            authenFeedback.setResultCode("105");
            authenFeedback.setReason(e.getMessage());
            return authenFeedback;
        } catch (Exception e) {
            logger.error("调用第三方失败", e);
            authenFeedback.setResultCode("105");
            authenFeedback.setReason(e.getMessage());
            return authenFeedback;
        } finally {
            httpThreadLocal.remove();
        }
        if (response == null) {
            return setFail(authenFeedback, "105", "调用第三方失败");
        }

        if (com.alibaba.dubbo.common.utils.StringUtils.isEquals(response.getSuccess(), "true")) {
            return setSuccess(authenFeedback, "实名信息上传成功");
        } else {
            return setFail(authenFeedback,
                    response.getResultCode() == null ? "105" : response.getResultCode(),
                    response.getResultCode() == null ? "实名信息上传失败" : response.getReason());
        }
    }

    private void checkData(AuthenFeedback authenFeedback, IdentityAuthenticationRequest request) {
        if (StringUtils.isEmpty(request.getName())) {
            setFail(authenFeedback, "105", "缺少真实姓名【Name】");
            return;
        }

        if (StringUtils.isEmpty(request.getTypeOfId())) {
            request.setTypeOfId("01");
//            setFail(authenFeedback, "105", "缺少证件类型代码【TypeOfId】");
//            return;
        }

        if (StringUtils.isEmpty(request.getNumberOfId())) {
            setFail(authenFeedback, "105", "缺少证件号【NumberOfId】");
            return;
        }

        if (StringUtils.isEmpty(request.getTypeOfUser())) {
            request.setTypeOfUser("1");
//            setFail(authenFeedback, "105", "缺少用户类别【TypeOfUser】");
//            return;
        }

        if (StringUtils.isEmpty(request.getCollectMethod())) {
            request.setCollectMethod("40");
//            setFail(authenFeedback, "105", "缺少用户信息采集方式【CollectMethod】");
//            return;
        }else if(request.getCollectMethod().length() == 1){
            request.setCollectMethod(request.getCollectMethod() + "0");
        }

        if (CollectionUtils.isEmpty(request.getContact())) {
            setFail(authenFeedback, "105", "缺少联系方式【Contactp】");
            return;
        }

        for (IdentityContactp contactp : request.getContact()) {
            if (StringUtils.isEmpty(contactp.getMobile()) && StringUtils.isEmpty(contactp.getPhone())) {
                setFail(authenFeedback, "105", "个人联系方式至少填写一个【Mobile|Phone】");
                return;
            }
        }

        if (CollectionUtils.isEmpty(request.getAddresses())) {
            setFail(authenFeedback, "105", "缺少地址信息【Addresses】");
            return;
        }

        for (IdentityAddresses addresses : request.getAddresses()) {
            if (StringUtils.isEmpty(addresses.getAddressType())) {
                addresses.setAddressType("2");
//                setFail(authenFeedback, "105", "缺少地址类型【AddressType】");
//                return;
            }
            if (StringUtils.isEmpty(addresses.getProvinceName())) {
                setFail(authenFeedback, "105", "缺少省、自治区或直辖市名称【ProvinceName】");
                return;
            }
            if (StringUtils.isEmpty(addresses.getProvinceCode())) {
                setFail(authenFeedback, "105", "缺少省、自治区或直辖市编码【ProvinceCode】");
                return;
            }
            if (StringUtils.isEmpty(addresses.getCityName())) {
                setFail(authenFeedback, "105", "缺少市名称【CityName】");
                return;
            }
            if (StringUtils.isEmpty(addresses.getCityCode())) {
                setFail(authenFeedback, "105", "缺少市编码【CityCode】");
                return;
            }
            if (StringUtils.isEmpty(addresses.getExpAreaName())) {
                setFail(authenFeedback, "105", "缺少县或区名称【ExpAreaName】");
                return;
            }
            if (StringUtils.isEmpty(addresses.getExpAreaCode())) {
                setFail(authenFeedback, "105", "缺少县或区编码【ExpAreaCode】");
                return;
            }
            if (StringUtils.isEmpty(addresses.getAddress())) {
                setFail(authenFeedback, "105", "缺少详细地址【Address】");
                return;
            }
        }
    }

    private AuthenFeedback setFail(AuthenFeedback authenFeedback, String code, String msg) {
        authenFeedback.setSuccess(false);
        authenFeedback.setResultCode(code);
        authenFeedback.setReason(msg);
        return authenFeedback;
    }

    private AuthenFeedback setSuccess(AuthenFeedback authenFeedback, String msg) {
        authenFeedback.setSuccess(true);
        authenFeedback.setResultCode("200");
        authenFeedback.setReason(msg);
        return authenFeedback;
    }

    private static void print(String msg, Object info) {
        if (info != null) {
            logger.info("【" + msg + "】：" + info);
//            System.out.println("【" + msg + "】：" + info);
        } else {
            logger.info("【" + msg + "】");
//            System.out.println("【" + msg + "】");
        }
    }
}
