package service.cloud.client3.start.data;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
public class MyUser implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private Date createTime;
    // 是否已过期
    private boolean accountNonExpired;
    // 是否锁定
    private boolean accountNonLocked;
    // 是否证书已过期
    private boolean credentialsNonExpired;
    // 是否可用
    private boolean enabled;
    // 用户权限
    private List<Permission> authorities = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
