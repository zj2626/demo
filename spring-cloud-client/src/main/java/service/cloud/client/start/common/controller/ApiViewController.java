//package service.cloud.client.start.common.controller;
//
//import common.core.site.controller.BaseViewController;
//import common.core.site.view.ViewContext;
//import common.core.web.api.view.api.method.ApiMethodInvokeObjectSimpleViewContext;
//import common.core.web.api.view.api.method.ApiMethodInvokeObjectViewContext;
//import common.core.web.api.view.method.ApiMethodInvokeObjectViewForm;
//import common.core.web.api.view.method.InvokeObjectSimpleView;
//import common.core.web.api.view.method.InvokeObjectView;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//public class ApiViewController extends BaseViewController {
//    @RequestMapping(value = "/system-admin/app/view/getApiMethodInvokeObjectView")
//    public String getApiMethodInvokeObjectView(ApiMethodInvokeObjectViewForm apiMethodInvokeObjectViewForm) {
//        InvokeObjectView result = ApiMethodInvokeObjectViewContext.getApiMethodInvokeObjectView(apiMethodInvokeObjectViewForm.getValue());
//        ViewContext.put("result", result);
//        return "apidocument/apiDocument";
//    }
//
//    @RequestMapping(value = "/system-admin/app/view/listApiMethodInvokeObjectView")
//    public String listApiMethodInvokeObjectView() {
//        List<InvokeObjectSimpleView> result = ApiMethodInvokeObjectSimpleViewContext.listApiMethodInvokeObjectView();
//        ViewContext.put("result", result);
//        return "apidocument/apiList";
//    }
//
//}
