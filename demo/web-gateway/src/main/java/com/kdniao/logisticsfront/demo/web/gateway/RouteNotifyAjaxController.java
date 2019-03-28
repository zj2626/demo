/**
 * Kdniao.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.kdniao.logisticsfront.demo.web.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kdniao.common.lang.error.ApplicationException;
import com.kdniao.common.lang.error.CommonError;
import com.kdniao.common.lang.error.ErrorType;
import com.kdniao.common.profile.digest.LoggerUtil;
import com.kdniao.common.web.constants.RequestAttributeKeys;
import com.kdniao.demo.common.util.Print;
import com.kdniao.logisticsfront.core.service.event.AsyncOrderStatusEventService;
import com.kdniao.logisticsfront.core.service.event.AsyncRouteQueryEventService;
import com.kdniao.logisticsfront.demo.core.model.constants.ApplicationErrors;
import com.kdniao.logisticsfront.demo.core.model.message.RouteNotifyRequest;
import com.kdniao.logisticsfront.demo.core.model.message.RouteNotifyResponse;
import com.kdniao.logisticsfront.demo.core.service.converter.RouteNotifyRequestConverter;
import com.kdniao.logisticsfront.demo.core.service.converter.RouteNotifyResponseConverter;
import com.kdniao.logisticsgw.common.service.event.RouteNotifyEvent;
import com.kdniao.logisticsgw.common.service.model.delivery.WaybillKey;
import com.kdniao.logisticsgw.common.service.model.enums.AcceptStatus;
import com.kdniao.logisticsgw.common.service.model.enums.OrderStatus;
import com.kdniao.logisticsgw.common.service.model.route.Route;
import com.kdniao.logisticsgw.common.service.model.route.Track;
import com.kdniao.logisticsgw.common.service.order.OrderRoute;
import com.kdniao.logisticsgw.common.service.order.OrderTrack;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * @version $Id: RouteNotifyAjaxController.java, v 0.1 2016年8月2日 下午12:23:55 Administrator Exp $
 */
@Controller
@RequestMapping("/gateway/routeNotify.json")
public class RouteNotifyAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(RouteNotifyAjaxController.class);
    private static final String DEFAULT_RESPONSE = "<Response><logisticProviderID>AY</logisticProviderID><txLogisticID>KDN3.0</txLogisticID><success>false</success></Response>";
    private static final String VALUE_RESULT_STATE_SUCCESS = "true";
    private static final String VALUE_RESULT_STATE_FAILED = "false";
    private static final DateFormat acceptTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String ENCODE = "UTF-8";

    private RouteNotifyResponseConverter responseConverter = new RouteNotifyResponseConverter();
    private RouteNotifyRequestConverter requestConverter = new RouteNotifyRequestConverter();
    private AsyncRouteQueryEventService asyncRouteQueryEventService;
    private AsyncOrderStatusEventService asyncOrderStatusEventService;

    public void setAsyncOrderStatusEventService(AsyncOrderStatusEventService asyncOrderStatusEventService) {
        this.asyncOrderStatusEventService = asyncOrderStatusEventService;
    }

    public void setAsyncRouteQueryEventService(AsyncRouteQueryEventService asyncRouteQueryEventService) {
        this.asyncRouteQueryEventService = asyncRouteQueryEventService;
    }

    // #################################
    // #################################
    // #################################
    @RequestMapping(method = {RequestMethod.POST})
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        RouteNotifyResponse notifyResponse = new RouteNotifyResponse();
        notifyResponse.setLogisticProviderID("DEMO");
        String content = request.getParameter("logisticsInterface");
        if (StringUtils.isEmpty(content)) {
            this.assemblerResponse(request, response, notifyResponse, false, RouteNotifyResponse.RESPONSE_CODE_EMPTY_REQUEST);
            logger.error("[ error message: logisticsInterface is empty !!!]");
            return;
        }

        try {
            content = URLDecoder.decode(content, ENCODE);
            logger.info(">>> " + content);
        } catch (UnsupportedEncodingException e) {
            throw new ApplicationException(ApplicationErrors.systemError.message("decode failed"), e);
        }

        RouteNotifyRequest notifyRequest;
        try {
            notifyRequest = JSON.parseObject(content, new TypeReference<RouteNotifyRequest>() {
            });
        } catch (Exception e) {
            LoggerUtil.error(logger, ApplicationErrors.systemError.message("parse request failed."), e);
            this.assemblerResponse(request, response, notifyResponse, false, RouteNotifyResponse.RESPONSE_CODE_INVALID_JSON);
            return;
        }

        // 需要进行下单消息的组装 所以放在判空之前
        // 获取推送信息内容
        Track track = new Track();
        OrderTrack orderTrack = new OrderTrack();

        if (notifyRequest.getCode() != null) {
            getMessage(track, orderTrack, notifyRequest);
            logger.info(JSON.toJSONString(track));
        } else {
            this.assemblerResponse(request, response, notifyResponse, false, RouteNotifyResponse.RESPONSE_CODE_EMPTY_REQUEST);
            logger.error("[ error message: code is empty !!!]");
            return;
        }

        //发送 订单kafka
        // sendOrder(notifyRequest, orderTrack);

        if (StringUtils.isEmpty(notifyRequest.getWaybillNo())) {
            // 已下单 分单成功 已取消 取件失败 四个状态不处理(没有waybillNo)
            if (notifyRequest.getCode() != null) {
                if (notifyRequest.getCode().equals("01") || notifyRequest.getCode().equals("02") || notifyRequest.getCode().equals("04") || notifyRequest.getCode().equals("07")) {
                    this.assemblerResponse(request, response, notifyResponse, true, "success");
                    return;
                }
            }

            this.assemblerResponse(request, response, notifyResponse, false, RouteNotifyResponse.RESPONSE_CODE_EMPTY_REQUEST);
            logger.error("[ error message: waybillNo is empty !!!]");
            return;
        }
        Route route = new Route();
        WaybillKey waybillKey = new WaybillKey();
        waybillKey.setShipperCode("DEMO");
        waybillKey.setWaybillCode(notifyRequest.getWaybillNo());
        route.setWaybillKey(waybillKey);
        route.addTrack(track);

        Print.out("轨迹推送demo ", route.getWaybillCode());

        //发送 轨迹kafka
        asyncRouteQueryEventService.send(route, RouteNotifyEvent.TRIGGER_TYPE_THIRDPARTY_NOTIFY);

        this.assemblerResponse(request, response, notifyResponse, true, "success");
    }

    private void getMessage(Track track, OrderTrack orderTrack, RouteNotifyRequest notifyRequest) {
        switch (notifyRequest.getCode()) {
            case "01": // 已下单
                track.setAcceptStatus(AcceptStatus.S0);
                track.setAcceptStation("已下单 ");
                orderTrack.setAcceptTime(new Date());
                orderTrack.setOrderStatus(OrderStatus.S0);
                orderTrack.setRemark(track.getAcceptStation());

                break;
            case "02": // 分单成功
                track.setAcceptStatus(AcceptStatus.S0);
                track.setAcceptStation("分单成功 > " + "业务员:[" + "] - 业务员手机:[" + "]");
                try {
                    track.setAcceptTime(acceptTimeFormat.parse(notifyRequest.getDeliveryTime()));
                } catch (ParseException pe) {
                    throw new ApplicationException(ApplicationErrors.invoke3thFailed.message("assignTime: invalid accept time format"));
                }
                orderTrack.setAcceptTime(track.getAcceptTime());
                orderTrack.setOrderStatus(OrderStatus.S01);
                orderTrack.setOperator("OrderStatus.S01");
                orderTrack.setRemark(track.getAcceptStation());
                break;
            case "04": // 已取消
                track.setAcceptStatus(AcceptStatus.S0);
                track.setAcceptStation("已取消 ");
                orderTrack.setAcceptTime(new Date());
                orderTrack.setOrderStatus(OrderStatus.S02);
                orderTrack.setRemark(track.getAcceptStation());
                break;
            case "05": // 已取件
                track.setAcceptStatus(AcceptStatus.S1);
                track.setAcceptStation("已取件 > " + "取件业务员:["  + "] - 取件业务员手机:[" + "]");
                try {
                    track.setAcceptTime(acceptTimeFormat.parse(notifyRequest.getDeliveryTime()));
                } catch (ParseException pe) {
                    throw new ApplicationException(ApplicationErrors.invoke3thFailed.message("takingTime: invalid accept time format"));
                }
                orderTrack.setAcceptTime(track.getAcceptTime());
                orderTrack.setOrderStatus(OrderStatus.S11);
                orderTrack.setOperator("AcceptStatus.S1");
                orderTrack.setRemark(track.getAcceptStation());
                break;
            case "07": // 取件失败
                track.setAcceptStatus(AcceptStatus.S4);
                track.setAcceptStation("取件失败 > ");
                try {
                    track.setAcceptTime(acceptTimeFormat.parse(notifyRequest.getDeliveryTime()));
                } catch (ParseException pe) {
                    throw new ApplicationException(ApplicationErrors.invoke3thFailed.message("abnormalTime: invalid accept time format"));
                }
                orderTrack.setAcceptTime(track.getAcceptTime());
                orderTrack.setOrderStatus(OrderStatus.S12);
                orderTrack.setRemark(track.getAcceptStation());
                break;
            case "08": // 派件中
                track.setAcceptStatus(AcceptStatus.S2);
                track.setAcceptStation("派件中 > " + "派件业务员:[" + "] - 派件业务员手机:[" + "]");
                try {
                    track.setAcceptTime(acceptTimeFormat.parse(notifyRequest.getDeliveryTime()));
                } catch (ParseException pe) {
                    throw new ApplicationException(ApplicationErrors.invoke3thFailed.message("deliveryTime: invalid accept time format"));
                }
                orderTrack.setAcceptTime(track.getAcceptTime());
                orderTrack.setOrderStatus(OrderStatus.S32);
                orderTrack.setOperator("OrderStatus.S32");
                orderTrack.setRemark(track.getAcceptStation());
                break;
            case "09": // 签收成功
                track.setAcceptStatus(AcceptStatus.S3);
                track.setAcceptStation("签收成功> " + "签收人:[" + "] ");
                try {
                    track.setAcceptTime(acceptTimeFormat.parse(notifyRequest.getDeliveryTime()));
                } catch (ParseException pe) {
                    throw new ApplicationException(ApplicationErrors.invoke3thFailed.message("signedTime: invalid accept time format"));
                }
                orderTrack.setAcceptTime(track.getAcceptTime());
                orderTrack.setOrderStatus(OrderStatus.S41);
                orderTrack.setOperator("OrderStatus.S41");
                orderTrack.setRemark(track.getAcceptStation());
                break;
            case "10": // 签收失败
                track.setAcceptStatus(AcceptStatus.S402);
                track.setAcceptStation("签收失败 > ");
                try {
                    track.setAcceptTime(acceptTimeFormat.parse(notifyRequest.getDeliveryTime()));
                } catch (ParseException pe) {
                    throw new ApplicationException(ApplicationErrors.invoke3thFailed.message("signedTime: invalid accept time format"));
                }
                orderTrack.setAcceptTime(track.getAcceptTime());
                orderTrack.setOrderStatus(OrderStatus.S41);
                orderTrack.setRemark(track.getAcceptStation());
                break;
        }
    }

    // #################################
    // #################################
    // #################################

    private void sendOrder(RouteNotifyRequest notifyRequest, OrderTrack track) {
        try {
            OrderRoute orderRoute = new OrderRoute();
            orderRoute.setShipperCode("DEMO");
            orderRoute.setKdnOrderCode(notifyRequest.getOrderLogisticsCode());//快递鸟订单号
            orderRoute.setLogisticCode(StringUtils.isEmpty(notifyRequest.getWaybillNo()) ? "" : notifyRequest.getWaybillNo());
            orderRoute.addTrack(track);
            asyncOrderStatusEventService.send(orderRoute, RouteNotifyEvent.TRIGGER_TYPE_THIRDPARTY_NOTIFY);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("asyncOrderStatusEvent Error", e);
        }
    }

    private void assemblerResponse(HttpServletRequest request, HttpServletResponse response, RouteNotifyResponse notifyResponse, boolean success, String reason) {
        notifyResponse.setSuccess(success ? VALUE_RESULT_STATE_SUCCESS : VALUE_RESULT_STATE_FAILED);
        notifyResponse.setReason(reason);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        notifyResponse.setTime(df.format(new Date()));
        notifyResponse.setStatus(success ? 200 : 500);
        notifyResponse.setMessage(reason);
        String responseString = null;
        try {
            responseString = JSON.toJSONString(notifyResponse);
        } catch (Exception e) {
            LoggerUtil.error(logger, ApplicationErrors.systemError.message("convert response to string failed."), e);
            responseString = DEFAULT_RESPONSE;
        }
        request.setAttribute(RequestAttributeKeys.AJAX_BODY, responseString);
        try {
            response.getWriter().write(responseString);
        } catch (IOException ioe) {
            LoggerUtil.error(logger, ApplicationErrors.systemError.message("send response failed"), ioe);
        }
    }

    @ExceptionHandler
    public void handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        CommonError error = null;
        if (ex instanceof ApplicationException) {
            error = ((ApplicationException) ex).getErrorContext().fetchCurrentError();
            LoggerUtil.log(logger, "请求处理失败：", error.getErrorLevel(), ex);
            error = ErrorType.BIZ.equals(error.getErrorType()) ? error : ApplicationErrors.systemError;
        } else {
            LoggerUtil.error(logger, "请求处理失败：", ex);
            error = ApplicationErrors.unknownError;
        }
        RouteNotifyResponse notifyResponse = new RouteNotifyResponse();
        request.setAttribute(RequestAttributeKeys.ERROR, error);
        this.assemblerResponse(request, response, notifyResponse, false, RouteNotifyResponse.RESPONSE_CODE_SYSTEM_ERROR);
    }

    public static String doSign(String logisticsInterface, String charset, String secretKey) {
        String sign;
        String content = logisticsInterface + secretKey;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes(charset));
            sign = new String(Base64.encodeBase64(md.digest()), charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sign;
    }
}