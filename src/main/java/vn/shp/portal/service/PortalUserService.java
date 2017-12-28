package vn.shp.portal.service;

import org.springframework.web.servlet.ModelAndView;
import vn.shp.portal.entity.PortalUser;
import vn.shp.portal.model.PortalUserBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PortalUserService {

	List<PortalUser> findAll();

	void save(PortalUser portalUser);
	
	PortalUser findOne(Long id);

	PortalUser findByUsername(String username);

	void delete(Long id);
	
	void delete(PortalUser entity);

	ModelAndView initSearch(PortalUserBean condition, HttpServletRequest request);

	List<PortalUser> searchByFilters(String username, String email, String fullName);

}
