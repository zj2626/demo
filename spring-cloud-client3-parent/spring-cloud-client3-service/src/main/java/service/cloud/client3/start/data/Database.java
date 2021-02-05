package service.cloud.client3.start.data;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 模拟数据库
 */
@Data
@Component
public class Database implements InitializingBean {

    // 用户+密码
    private Map<String, String> userInfo = new HashMap<>();

    // 用户-角色
    private Map<String, List<String>> userRoles = new HashMap<>();

    // 角色-权限
    private Map<String, List<String>> rolePermissions = new HashMap<>();

    // 权限-角色
    private Map<String, List<String>> permissionsRoles = new HashMap<>();

    // url-权限
    private Map<String, String> urlPermission = new HashMap<>();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 用户信息
        userInfo.put("user1", passwordEncoder.encode("000000"));
        userInfo.put("user2", passwordEncoder.encode("111111"));
        userInfo.put("admin", passwordEncoder.encode("111111"));
        userInfo.put("dba", passwordEncoder.encode("111111"));

        // 用户拥有的角色
        userRoles.put("user1", new ArrayList<>(Arrays.asList("GoodsManager")));
        userRoles.put("user2", new ArrayList<>(Arrays.asList("GoodsManager", "OrderManager")));
        userRoles.put("dba", new ArrayList<>(Arrays.asList("DBAManager")));
        userRoles.put("admin", new ArrayList<>(Arrays.asList("Administrator")));

        // 角色对应的权限
        rolePermissions.put("GoodsManager", new ArrayList<>(Arrays.asList("home", "user", "add")));
        rolePermissions.put("OrderManager", new ArrayList<>(Arrays.asList("home", "user", "alter")));
        rolePermissions.put("DBAManager", new ArrayList<>(Arrays.asList("home", "db")));
        rolePermissions.put("Administrator", new ArrayList<>(Arrays.asList("home", "user", "add", "alter", "delete", "db")));

        Set<String> permiStringList = new HashSet<>();
        for (List<String> permissions : rolePermissions.values()) {
            permiStringList.addAll(permissions);
        }
        for (String permission : permiStringList) {
            List<String> roles = new ArrayList<>();
            for (Map.Entry<String, List<String>> stringListEntry : rolePermissions.entrySet()) {
                if (stringListEntry.getValue().contains(permission)) {
                    roles.add(stringListEntry.getKey());
                }
            }
            permissionsRoles.put(permission, roles);
        }

        /*
        urlPermission.put("/home", new ArrayList<>(Arrays.asList("Administrator", "GoodsManager", "OrderManager", "DBAManager")));
        urlPermission.put("/user", new ArrayList<>(Arrays.asList("Administrator", "GoodsManager", "OrderManager")));
        urlPermission.put("/user/add", new ArrayList<>(Arrays.asList("Administrator", "GoodsManager")));
        urlPermission.put("/user/alter", new ArrayList<>(Arrays.asList("Administrator", "OrderManager")));
        urlPermission.put("/admin/delete", new ArrayList<>(Arrays.asList("Administrator")));
        urlPermission.put("/db/**", new ArrayList<>(Arrays.asList("Administrator", "DBAManager")));
        * */

        urlPermission.put("/home", "home");
        urlPermission.put("/user", "user");
        urlPermission.put("/user/add", "add");
        urlPermission.put("/user/alter", "alter");
        urlPermission.put("/admin/delete", "delete");
        urlPermission.put("/db/**", "db");
    }

    public MyUser getUserInfoByName(String username) {
        String password = userInfo.get(username);
        List<String> roles = userRoles.get(username);
        List<Permission> permissionList = new ArrayList<>();
        if (null != roles) {
            Set<String> permissions = new HashSet<>();
            for (String role : roles) {
                permissions.addAll(rolePermissions.get(role));
            }
            for (String permission : permissions) {
                Permission p = new Permission();
                p.setName(permission);
                permissionList.add(p);
            }
        }

        MyUser user = new MyUser();
        user.setId(new Random().nextInt(100));
        user.setUsername(username);
        user.setPassword(password);
        user.setCreateTime(new Date());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setAuthorities(permissionList);
        return user;
    }

    public boolean getUserByName(String username) {
        return null != userInfo.get(username);
    }

    public String getPasswordByName(String username) {
        return userInfo.get(username);
    }

    public List<String> getUserRolesById(String username) {
        return userRoles.get(username);
    }

    public List<String> getRolePermissions(String role) {
        return rolePermissions.get(role);
    }

    public List<String> getPermissionRoles(String permission) {
        return permissionsRoles.get(permission);
    }

    public Set<String> getUrls() {
        return urlPermission.keySet();
    }

    public String getPermission(String url) {
        return urlPermission.get(url);
    }

    public Map<String, String> getAllPermission() {
        return urlPermission;
    }

    public Map<String, ConfigAttribute> getUrlPermission() {
        Map<String, ConfigAttribute> result = new HashMap<>();
        for (Map.Entry<String, String> entry : urlPermission.entrySet()) {
            result.put(entry.getKey(), new SecurityConfig(entry.getValue()));
        }
        return result;
    }
}
