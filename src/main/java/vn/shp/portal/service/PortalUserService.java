package vn.shp.portal.service;

import org.springframework.web.servlet.ModelAndView;
import vn.shp.portal.entity.PortalUser;
import vn.shp.portal.model.PortalUserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PortalUserService {

	List<PortalUser> findAll();

	void save(PortalUser portalUser);
	
	PortalUser findOne(Long id);

	PortalUser findByUsername(String username);

	void delete(Long id);
	
	void delete(PortalUser entity);

	ModelAndView initSearch(PortalUserModel condition, HttpServletRequest request);

}
