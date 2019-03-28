
package com.kdniao.logisticsfront.demo.biz.electron.impl;

import com.alibaba.fastjson.JSON;
import com.kdniao.demo.common.util.Print;
import com.kdniao.logisticsfront.biz.shared.electron.impl.AbstractElectronOrderManagerImpl;
import com.kdniao.logisticsgw.common.service.model.order.ExpElectronOrder;
import com.kdniao.logisticsgw.common.service.model.privacy.PhoneBindResult;
import com.kdniao.logisticsgw.common.service.order.ElectronOrder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

public class ElectronOrderInvokeManagerImpl extends AbstractElectronOrderManagerImpl {
    private static final Logger logger = LoggerFactory.getLogger(ElectronOrderInvokeManagerImpl.class);
    private static final String ENCODE = "utf-8";

    @Override
    protected ExpElectronOrder doInvoke(ElectronOrder order) {
        // #################
//        PhoneBindResult result = privacy(order, order.getReceiverTel());
//        System.out.println(JSON.toJSONString(result));
        // #################


        Print.out("电子面单demo ", order.getOrderCode());

        ExpElectronOrder expOrder = new ExpElectronOrder();
        expOrder.setResultCode("105");
        expOrder.setReason("未知错误");

        expOrder.setSuccess(true);
        expOrder = checkData(order, expOrder);
        if (!expOrder.isSuccess()) return expOrder;
        expOrder.setSuccess(false);

        Print.out("order ", JSON.toJSONString(order) + '\n');

        expOrder.setKdnOrderCode(order.getKdnOrderCode());
        expOrder.setLogisticCode(StringUtils.isEmpty(order.getLogisticCode())
                ? new SimpleDateFormat("yyyyMMdd").format(new Date()) + getFixLenthString(4)
                : order.getLogisticCode());
        expOrder.setSuccess(true);
        expOrder.setResultCode("100");
        expOrder.setReason("成功");
        expOrder.setPrintTemplate("this is a template of demo GG (from project [DEMO])");
        if (null != order.getQuantity()) {
            expOrder.setSubCount(order.getQuantity());
            List<String> subOrders = new ArrayList<>();
            List<String> subPrintTemplates = new ArrayList<>();
            for (int i = 0; i < order.getQuantity(); i++) {
                String code = new SimpleDateFormat("MMdd").format(new Date()) + getFixLenthString(8);
                subOrders.add(code);
                subPrintTemplates.add("this is a template of demo ( " + code + " ) (from project [DEMO]) of " + i + "\t");
            }
            expOrder.setSubOrders(subOrders);
            expOrder.setSubPrintTemplates(subPrintTemplates);
        }
        expOrder.setShipperCode(order.getShipperCode());

        return expOrder;
    }

    private ExpElectronOrder checkData(ElectronOrder order, ExpElectronOrder expOrder) {
        // 请求信息检查
//        if (StringUtils.isEmpty(order.getCustomerName())) {
//            return returnError(expOrder, "客户号必填【customerName】");
//        }
//        if (StringUtils.isEmpty(order.getCustomerPwd())) {
//            return returnError(expOrder, "客户密码必填【customerPwd】");
//        }

        return expOrder;
    }

    /**
     * 设置请求头信息
     *
     * @return
     */
    private Map<String, String> makeHead(String apiToken) {
        // 组装请求头信息
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "basic " + apiToken);
        headerMap.put("Content-Type", "text/xml; charset=utf-8");
        return headerMap;
    }

    private ExpElectronOrder returnError(ExpElectronOrder expOrder, String msg) {
        expOrder.setSuccess(false);
        expOrder.setResultCode("R301");
        expOrder.setReason(msg);
        return expOrder;
    }

    /*
     * 返回长度为【strLength】的随机数，在前面补0
     */
    private static String getFixLenthString(int strLength) {
        Random rm = new Random();
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }

    @Override
    protected ExpElectronOrder doInvoke(ElectronOrder order, ExpElectronOrder expElectronOrder) {
        expElectronOrder.setPrintTemplate(" this is template");
        expElectronOrder.setSuccess(true);
        expElectronOrder.setResultCode("100");
        expElectronOrder.setReason("成功");
        return expElectronOrder;
    }

    public static void main(String[] args) {
        ElectronOrderInvokeManagerImpl eomi = new ElectronOrderInvokeManagerImpl();

        String data = "{\"addServiceList\":" +
                "[" +
                "{\"name\":\"INSURE\",\"value\":\"58\"}," +
//                "{\"name\":\"COD\",\"value\":\"300\",\"customerId\":\"7555025135\"}" +
                "]," +
                "\"commodityList\":" +
                "[" +
                "{\"goodsCode\":\"0010\",\"goodsCodeDesc\":\"葛瑞格\",\"goodsName\":\"玫瑰花飞\",\"goodsPrice\":1200,\"Goodsquantity\":\"1000\"}," +
                "{\"goodsCode\":\"0011\",\"goodsCodeDesc\":\"葛瑞格2\",\"goodsName\":\"玫瑰花飞2\",\"goodsPrice\":9}" +
                "]," +
                "\"expType\":13," +
                "\"isReturnPrintTemplate\":\"1\"," +
                "\"kdnOrderCode\":\"" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + getFixLenthString(4) + "\"," +
                "\"logisticCodeType\":false," +
//                "\"monthCode\":\"7555025135\"," +
                "\"orderCode\":\"" + getFixLenthString(4) + "\"," +
                "\"payType\":1," +
                "\"receiverAddress\":\"广州大学城外环路102号102号102号102号102号102号102号\"," +
                "\"receiverArea\":\"蓬江区\"," +
                "\"receiverCity\":\"江门市\"," +
                "\"receiverCompany\":\"广州快信光学科技有限公司\"," +
                "\"receiverMobile\":\"123-1212190\"," +
                "\"receiverName\":\"上官张三\"," +
                "\"receiverPostCode\":\"10012\"," +
                "\"receiverProvince\":\"广东省\"," +
                "\"receiverTel\":\"13259612993\"," +
                "\"senderAddress\":\"华强北赛格大厦5401 \"," +
                "\"senderArea\":\"福田区\"," +
                "\"senderCity\":\"深圳市\"," +
                "\"senderCompany\":\"酷视（深圳）贸易有限公司\"," +
                "\"senderTel\":\"7501200\"," +
                "\"senderMobile\":\"-\"," +
                "\"senderTel\":\"123-4567890\"," +
                "\"senderName\":\"慕容李四\"," +
                "\"senderProvince\":\"广东省\"," +
                "\"shipperCode\":\"SF\"," +
                "\"isNotice\":1," +
//                "\"startDate\":1545018943871," +
                "\"quantity\":1," +
                "\"isReturnSignBill\":\"1\"," +
                "\"templateSize\":\"150\"," +
                "\"uniquerRequestNumber\":\"1545018943871\"," +
                "\"remark\":\"测试测试测试测试测试测试fffffffff测试fffffff测试fffff remark 测试\"," +
                "\"weight\":1}";
        ElectronOrder order = JSON.parseObject(data, ElectronOrder.class);
        Print.out("", JSON.toJSONString(eomi.doInvoke(order)));
    }
}
