package service.cloud.client3.start.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import service.cloud.client3.start.data.MyUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        MyUser user = (MyUser) authentication.getPrincipal();
        System.out.println(user.getUsername() + " 退出登录... ");
    }
}
