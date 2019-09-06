package service.cloud.feign.start.service;

import org.springframework.cloud.openfeign.FeignClient;
import service.cloud.client.start.controller2.ControllerApi;

@FeignClient(value = "spring-cloud-client02")
public interface InterfaceFunService extends ControllerApi {
}
