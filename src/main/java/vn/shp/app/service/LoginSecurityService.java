package vn.shp.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.shp.app.config.UserProfile;
import vn.shp.app.entity.PortalGroup;
import vn.shp.app.entity.PortalRole;
import vn.shp.app.entity.PortalUser;
import vn.shp.app.repository.PortalUserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hant1 on 13/07/2017.
 */
@Service(value = "loginSecurityService")
public class LoginSecurityService implements UserDetailsService {

    @Autowired
    private PortalUserRepository userDao;

    @Autowired
    UserProfile userProfile;

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        PortalUser user =  userDao.findByUsername(userId);




        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        userProfile.setUser(user);
        //return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getEnabled(), true, true, true, getAuthority(user));
    }

    private List getAuthority(PortalUser user) {
        List<SimpleGrantedAuthority> lstAuth = new ArrayList<>();
        List<PortalGroup> lstGroup = user.getGroups();
        if(!CollectionUtils.isEmpty(lstGroup)){
            for (PortalGroup portalGroup : lstGroup) {
               List<PortalRole> lstRoles = portalGroup.getRoleGroupLst();
               if(!CollectionUtils.isEmpty(lstRoles)){
                   for (PortalRole role : lstRoles) {
                       lstAuth.add(new SimpleGrantedAuthority(role.getRoleCode()));
                   }

               }
            }
            return lstAuth;
        }

        return Arrays.asList(new SimpleGrantedAuthority("ROLE_AUTH"));
    }
}
