package vn.shp.portal.service;

import vn.shp.portal.entity.PortalGroup;

import java.util.List;

public interface PortalGroupService {

	void save(PortalGroup entity);

	List<PortalGroup> findAll();

	PortalGroup findOne(Long id);

	void delete(Long GroupId);

	PortalGroup findByGroupName(String groupName);

	PortalGroup findByGroupCode(String groupCode);

	List<PortalGroup> searchByFilters(String groupName, String groupCode);
	
}
