package service.cloud.client3.start.data;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Permission implements GrantedAuthority {
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
