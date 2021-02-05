package service.cloud.client3.start.filter;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * 决策管理器，用来投票决定是否放行请求 判断用户是否有访问权限
 * <p>
 * AccessDecisionManager 有三个默认实现：
 * 1. AffirmativeBased 基于肯定的决策器。 用户持有一个同意访问的角色就能通过。
 * 2. ConsensusBased 基于共识的决策器。 用户持有同意的角色数量多于禁止的角色数。
 * 3. UnanimousBased 基于一致的决策器。 用户持有的所有角色都同意访问才能放行。
 * Created by macro on 2020/2/7.
 */
@Component
public class CustomerPermissionAccessDecisionManager implements AccessDecisionManager {

    /**
     * 对用户访问的url进行验证, 看有没有这个地址的访问权限
     *
     * @param authentication   包含用户的信息和权限信息, 在登录的时候放进去 [SecurityContextHolder.getContext().setAuthentication()]
     * @param object           FilterInvocation的实现对象, 封装了请求响应信息
     * @param configAttributes 该请求地址的权限集合
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (CollectionUtils.isEmpty(configAttributes)) {
            return;
        }
        Collection<? extends GrantedAuthority> userGrantedAuthorities = authentication.getAuthorities();
        System.out.println(Thread.currentThread().getName() + " DDDD > 开始验证用户权限 需要权限: " + configAttributes + " > 用户已有权限: " + userGrantedAuthorities);
        for (ConfigAttribute configAttribute : configAttributes) {
            String needAuthority = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : userGrantedAuthorities) {
                if (grantedAuthority.getAuthority().equals(needAuthority.trim())) {
                    return;
                }
            }
        }

        System.out.println(Thread.currentThread().getName() + " DDDD > 验证失败 " + userGrantedAuthorities);
        throw new AccessDeniedException("没有访问权限: " + configAttributes);
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
