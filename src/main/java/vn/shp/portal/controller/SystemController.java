package vn.shp.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.shp.app.bean.SystemBean;
import vn.shp.app.config.Constants;
import vn.shp.portal.entity.JsonReturn;
import vn.shp.portal.entity.PortalUser;
import vn.shp.portal.service.PortalUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("system")
public class SystemController extends AbstractController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    PortalUserService portalUserService;

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN,Role.ROLE_LIST_ACTIVE_USER)")
    @RequestMapping(value = "/user/active", method = RequestMethod.GET)
    public String listAccountActiveSessionGet(@ModelAttribute(value = "bean") SystemBean bean, Model model,
                                              HttpServletRequest request, Locale locale) throws Exception {

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Object[] obj = authorities.toArray();
        bean = new SystemBean();

        List<SessionInformation> activeSessions = new ArrayList<SessionInformation>();
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            List<SessionInformation> userSessions = sessionRegistry.getAllSessions(principal, false);
            for (SessionInformation sessionInformation : userSessions) {
                activeSessions.add(sessionInformation);
            }
        }

        List<PortalUser> lstAccount = new ArrayList<PortalUser>();
        for (SessionInformation sessionInformation : activeSessions) {
            User user = (User) sessionInformation.getPrincipal();
            PortalUser account = portalUserService.findByUsername(user.getUsername());
            if (account != null) {
                account.setSessionId(sessionInformation.getSessionId());
                account.setLastAction(sessionInformation.getLastRequest());
                lstAccount.add(account);
            }
        }
        bean.setLstUser(lstAccount);

        model.addAttribute("bean", bean);
        return "system/active_user_list";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN,Role.ROLE_LOGOUT_USER)")
    @RequestMapping(value = "/user/ajax_logout", method = RequestMethod.GET)
    public @ResponseBody
    JsonReturn ajaxLogoutUser(@RequestParam(value = "sessionId", required = true) String sessionId, Model model) {
        JsonReturn res = new JsonReturn();

        for (Object principal : sessionRegistry.getAllPrincipals()) {
            List<SessionInformation> userSessions = sessionRegistry.getAllSessions(principal, false);
            for (SessionInformation sessionInformation : userSessions) {
               if(sessionInformation.getSessionId().equals(sessionId)){
                   sessionInformation.expireNow();
                   res.setStatus(Constants.SUCCESS);
               }
            }


        }

        return res;
    }
}
