package vn.shp.portal.service;

import vn.shp.portal.entity.PortalGroup;
import vn.shp.portal.model.PortalGroupModel;

import java.util.List;

public interface PortalGroupService {

	void save(PortalGroup entity);

	List<PortalGroup> findAll();

	PortalGroupModel findByData(PortalGroupModel portalGroupModel);

	PortalGroup findOne(Long id);

	void delete(Long GroupId);

	PortalGroup findByGroupName(String groupName);
	
}
