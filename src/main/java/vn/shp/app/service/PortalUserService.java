package vn.shp.app.service;

import org.springframework.web.servlet.ModelAndView;
import vn.shp.app.entity.PortalUser;
import vn.shp.app.bean.PortalUserBean;

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
