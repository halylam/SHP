package vn.shp.app.service;

import org.springframework.web.servlet.ModelAndView;
import vn.shp.app.entity.PortalRole;
import vn.shp.app.bean.PortalRoleBean;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

public interface PortalRoleService {

	void save(PortalRole entity);

	List<PortalRole> findAll();

	PortalRole findOne(Long id);

	ModelAndView initSearch(PortalRoleBean portalRoleModel, HttpServletRequest request);

	void delete(Long roleId);

	List<PortalRole> searchByFilters(String roleName, String roleCode);

    PortalRole findByRoleCode(String roleCode);
}
