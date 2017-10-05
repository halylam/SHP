package vn.shp.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import vn.shp.portal.entity.PortalUser;
import vn.shp.portal.repository.PortalUserRepository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hant1 on 13/07/2017.
 */
@Component(value = "userDetailService")
public class LoginSecurityService implements UserDetailsService {

    @Autowired
    private PortalUserRepository userDao;

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        PortalUser user =  new PortalUser("nguyenha","nguyenha");//userDao.findByUsername(userId);

        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password11.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), "password1", getAuthority());
    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public List getUsers() {
        return userDao.findAll();
    }
}
