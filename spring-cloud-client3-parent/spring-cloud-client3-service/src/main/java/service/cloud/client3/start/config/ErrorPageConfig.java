package service.cloud.client3.start.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorPageConfig {
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        ErrorPage page400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400");
        ErrorPage page401 = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401");
        ErrorPage page403 = new ErrorPage(HttpStatus.FORBIDDEN, "/error/403");
        ErrorPage page404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
        ErrorPage page405 = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error/405");
        ErrorPage page500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
        factory.addErrorPages(page400, page401, page403, page404, page405, page500);
        return factory;
    }
}
