package vn.shp.portal.service;

import vn.shp.portal.entity.PortalSystemGroup;

import java.util.List;

public interface PortalSystemGroupService {

	void save(PortalSystemGroup entity);

	List<PortalSystemGroup> findAll();

	List<PortalSystemGroup> findByData(PortalSystemGroup portalGroup);

	PortalSystemGroup findOne(Long id);

	void delete(Long groupId);

	PortalSystemGroup findByGroupName(String groupName);

}
