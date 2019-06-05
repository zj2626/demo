/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.demo.core.service.converter;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;

import com.kdniao.common.lang.error.ApplicationException;
import com.kdniao.logisticsfront.demo.core.model.constants.ApplicationErrors;
import com.kdniao.logisticsfront.demo.core.model.message.OrderStatusNotifyRequest;

/**
 * 
 * @author wensl
 * @version $Id: OrderStatusNotifyRequestConverter.java, v 0.1 2017年7月26日 下午4:55:25 wensl Exp $
 */
public class OrderStatusNotifyRequestConverter {

    private Unmarshaller requestUnmarshaller;

    public OrderStatusNotifyRequestConverter() {
        try {
            JAXBContext responseJaxbContext = JAXBContext.newInstance(OrderStatusNotifyRequest.class);
            this.requestUnmarshaller = responseJaxbContext.createUnmarshaller();
        } catch (JAXBException jaxbe) {
            throw new ApplicationException(ApplicationErrors.systemError.message("初始化unmarshaller出错"), jaxbe);
        }
    }

    public OrderStatusNotifyRequest fromString(String string) {
        StringReader requestReader = new StringReader(string);
        try {
            return (OrderStatusNotifyRequest) this.requestUnmarshaller.unmarshal(requestReader);
        } catch (JAXBException jaxbe) {
            throw new ApplicationException(ApplicationErrors.systemError.message("反序列化响应出错"), jaxbe);
        } finally {
            IOUtils.closeQuietly(requestReader);
        }
    }
}