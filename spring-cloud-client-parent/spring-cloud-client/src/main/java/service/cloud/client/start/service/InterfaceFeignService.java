package service.cloud.client.start.service;

        import org.springframework.cloud.openfeign.FeignClient;
        import service.cloud.client.start.controller.api.ControllerApi;
        import service.cloud.client.start.service.fallback.SchedualServiceHiHystric;

//@FeignClient(value = "spring-cloud-client02")
@FeignClient(value = "spring-cloud-client02", fallback = SchedualServiceHiHystric.class)
public interface InterfaceFeignService extends ControllerApi {
}
