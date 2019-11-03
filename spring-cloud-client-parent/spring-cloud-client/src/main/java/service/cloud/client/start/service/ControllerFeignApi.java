package service.cloud.client.start.service;

import org.springframework.cloud.openfeign.FeignClient;
import service.cloud.client2.start.controller.api.ControllerApi;

@FeignClient(value = "spring-cloud-client02")
//@FeignClient(value = "spring-cloud-client02", fallback = SchedualServiceHiHystric.class)
public interface ControllerFeignApi extends ControllerApi {
}
