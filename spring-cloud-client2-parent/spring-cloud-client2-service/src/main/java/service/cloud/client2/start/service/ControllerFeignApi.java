package service.cloud.client2.start.service;

import org.springframework.cloud.openfeign.FeignClient;
import service.cloud.client3.start.controller.api.ControllerApi;

@FeignClient(value = "spring-cloud-client03")
public interface ControllerFeignApi extends ControllerApi {
}
