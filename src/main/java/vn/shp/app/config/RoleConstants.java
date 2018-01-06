package vn.shp.app.config;

import org.springframework.stereotype.Component;

@Component(value = "Role")
public class RoleConstants {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_LIST_ACTIVE_USER = "ROLE_LIST_ACTIVE_USER";
    public static final String ROLE_LOGOUT_USER = "ROLE_LOGOUT_USER";
}
