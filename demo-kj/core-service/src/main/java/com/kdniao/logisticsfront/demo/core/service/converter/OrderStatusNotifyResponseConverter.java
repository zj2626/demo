/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.demo.core.service.converter;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.IOUtils;

import com.kdniao.common.lang.error.ApplicationException;
import com.kdniao.logisticsfront.demo.core.model.constants.ApplicationErrors;
import com.kdniao.logisticsfront.demo.core.model.message.OrderStatusNotifyResponse;

/**
 * 
 * @author wensl
 * @version $Id: OrderStatusNotifyResponseConverter.java, v 0.1 2017年7月26日 下午4:55:25 wensl Exp $
 */
public class OrderStatusNotifyResponseConverter {

    private JAXBContext responseJaxbContext;

    public OrderStatusNotifyResponseConverter() {
        try {
            this.responseJaxbContext = JAXBContext.newInstance(OrderStatusNotifyResponse.class);
        } catch (JAXBException jaxbe) {
            throw new ApplicationException(ApplicationErrors.systemError.message("初始化marshaller出错"), jaxbe);
        }
    }

    public String toString(OrderStatusNotifyResponse response) {
        StringWriter responseWriter = new StringWriter();
        try {
            Marshaller responseMarshaller = responseJaxbContext.createMarshaller();
            responseMarshaller.marshal(response, responseWriter);
            return responseWriter.toString();
        } catch (JAXBException jaxbe) {
            throw new ApplicationException(ApplicationErrors.systemError.message("序列化请求出错"), jaxbe);
        } finally {
            IOUtils.closeQuietly(responseWriter);
        }
    }
}