package vn.shp.app.config;

import org.springframework.stereotype.Component;

@Component(value = "Role")
public class RoleConstants {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_LIST_ACTIVE_USER = "ROLE_LIST_ACTIVE_USER";
    public static final String ROLE_LOGOUT_USER = "ROLE_LOGOUT_USER";

    public static final String ROLE_HOCVIEN_CREATE = "ROLE_HOCVIEN_CREATE";
    public static final String ROLE_HOCVIEN_EDIT="ROLE_HOCVIEN_EDIT";
    public static final String ROLE_HOCVIEN_LIST="ROLE_HOCVIEN_LIST";

    public static final String ROLE_GIANGVIEN_CREATE = "ROLE_GIANGVIEN_CREATE";
    public static final String ROLE_GIANGVIEN_EDIT="ROLE_GIANGVIEN_EDIT";
    public static final String ROLE_GIANGVIEN_LIST="ROLE_GIANGVIEN_LIST";
}
