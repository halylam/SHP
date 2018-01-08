package vn.shp.app.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.shp.app.entity.PortalUser;
import vn.shp.app.service.PortalUserService;

import java.util.List;

@Component(value = "userProfile")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class UserProfile {

    @Autowired
    private PortalUserService portalUserService;

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


    public PortalUser getUser() {
        if(user == null){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName(); //get logged in username
            user = portalUserService.findByUsername(name);


        }
        return user;
    }

    public void setUser(PortalUser user) {

        this.user = user;
    }
}
