package vn.shp.portal.service;

import org.springframework.web.servlet.ModelAndView;
import vn.shp.portal.entity.PortalRole;
import vn.shp.portal.model.PortalRoleModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PortalRoleService {

	void save(PortalRole entity);

	List<PortalRole> findAll();

	PortalRole findOne(Long id);

	ModelAndView initSearch(PortalRoleModel portalRoleModel, HttpServletRequest request);

	void delete(Long roleId);
	
}
