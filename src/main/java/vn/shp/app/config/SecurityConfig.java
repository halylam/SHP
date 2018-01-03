package vn.shp.app.config;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;
import vn.shp.app.service.LoginSecurityService;
import vn.shp.portal.entity.PortalGroup;
import vn.shp.portal.entity.PortalRole;
import vn.shp.portal.entity.PortalUser;
import vn.shp.portal.service.PortalUserService;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter implements AuthenticationProvider{

	@Autowired
	PortalUserService portalUserService;

	@Autowired
	@Qualifier(value = "userProfile")
	UserProfile userProfile;

	@Autowired
	LoginSecurityService loginSecurityService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(loginSecurityService);
		auth.authenticationProvider(this);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("protected void configure(HttpSecurity http) throws Exception");
		http
				.authorizeRequests()
				.antMatchers("/mcr/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
				.logout()
				.permitAll();
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		List<GrantedAuthority> authoritiesRs = new ArrayList<GrantedAuthority>();
		PortalUser portalUser = portalUserService.findByUsername(username);
		if (!CollectionUtils.isEmpty(portalUser.getGroups())) {
			for (PortalGroup group : portalUser.getGroups()) {
				for (PortalRole role : group.getRoleGroupLst()) {
					GrantedAuthority grantedAuthorityImpl = new SimpleGrantedAuthority(role.getRoleCode());
					authoritiesRs.add(grantedAuthorityImpl);
				}
			}
		}

		authoritiesRs.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return new UsernamePasswordAuthenticationToken(username, password, authoritiesRs);
//		return new UsernamePasswordAuthenticationToken(username, password, authoritiesRs);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}


}