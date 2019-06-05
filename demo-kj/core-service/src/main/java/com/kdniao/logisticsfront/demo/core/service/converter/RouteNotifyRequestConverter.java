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
import com.kdniao.logisticsfront.demo.core.model.message.RouteNotifyRequest;

/**
 * 
 * @author huangboke
 * @version $Id: RouteNotifyRequestConverter.java, v 0.1 2017年7月26日 下午4:55:25 huangboke Exp $
 */
public class RouteNotifyRequestConverter {

    private Unmarshaller requestUnmarshaller;

    public RouteNotifyRequestConverter() {
        try {
            JAXBContext responseJaxbContext = JAXBContext.newInstance(RouteNotifyRequest.class);
            this.requestUnmarshaller = responseJaxbContext.createUnmarshaller();
        } catch (JAXBException jaxbe) {
            throw new ApplicationException(ApplicationErrors.systemError.message("初始化unmarshaller出错"), jaxbe);
        }
    }

    public RouteNotifyRequest fromString(String string) {
        StringReader requestReader = new StringReader(string);
        try {
            return (RouteNotifyRequest) this.requestUnmarshaller.unmarshal(requestReader);
        } catch (JAXBException jaxbe) {
            throw new ApplicationException(ApplicationErrors.systemError.message("反序列化响应出错"), jaxbe);
        } finally {
            IOUtils.closeQuietly(requestReader);
        }
    }
}