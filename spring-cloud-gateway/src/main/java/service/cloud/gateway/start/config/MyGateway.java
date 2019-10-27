package service.cloud.gateway.start.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyGateway {

    @Value("${my.gateway.routes.uri01}")
    private String service01;

    @Value("${my.gateway.routes.uri02}")
    private String service02;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                //basic proxy
                .route(r -> r.path("/api-s1/**")
                        .filters(f -> f
                                .stripPrefix(1)
                        )
                        .uri(service01)
                )
                .route(r -> r.path("/api-s2/**")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("service02")
                                        .setFallbackUri("forward:/user/fallback"))
                                .stripPrefix(1)
                        )
                        .uri(service02)
                ).build();
    }

}
