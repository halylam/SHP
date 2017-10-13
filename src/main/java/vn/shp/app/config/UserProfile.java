package vn.shp.app.config;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import vn.shp.portal.entity.PortalUser;

import java.util.List;

@Component(value = "userProfile")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class UserProfile {

    private PortalUser user;
    private String defaultLang;
    private List<Long> authorizedDepartment;

    public String getDefaultLang() {
        return defaultLang;
    }

    public void setDefaultLang(String defaultLang) {
        this.defaultLang = defaultLang;
    }


    public List<Long> getAuthorizedDepartment() {
        return authorizedDepartment;
    }

    public void setAuthorizedDepartment(List<Long> authorizedDepartment) {
        this.authorizedDepartment = authorizedDepartment;
    }


}
