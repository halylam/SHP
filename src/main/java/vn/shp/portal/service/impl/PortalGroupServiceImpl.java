package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import vn.shp.portal.entity.PortalGroup;
import vn.shp.portal.model.PortalGroupModel;
import vn.shp.portal.repository.PortalGroupRepository;
import vn.shp.portal.service.PortalGroupService;

import java.util.List;

@Service("PortalGroupService")
public class PortalGroupServiceImpl implements PortalGroupService {

	@Autowired
	private PortalGroupRepository repo;
	
	@Override
	public List<PortalGroup> findAll() {
		return repo.findAll();
	}

	@Override
	@Transactional
	public void save(PortalGroup entity) {
		repo.save(entity);
	}
	
	@Override
	public PortalGroupModel findByData(PortalGroupModel portalGroupModel) {
		PortalGroup entity = portalGroupModel.getEntity();
		List<PortalGroup> result = null;
		
		if (entity != null && (!StringUtils.isEmpty(entity.getGroupName()) || !StringUtils.isEmpty(entity.getStatus()))) {
			result = repo.findByGroupNameContainingOrStatusContainingOrderByGroupIdDesc(portalGroupModel.getEntity().getGroupName(), portalGroupModel.getEntity().getStatus());
		} else {
			result = repo.findByOrderByGroupIdDesc();
		}

		portalGroupModel.setData(result);
		return portalGroupModel;
	}
	
	@Override
	public PortalGroup findOne(Long id) {
		return repo.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long GroupId) {
		repo.delete(GroupId);
	}

	@Override
	public PortalGroup findByGroupName(String groupName) {
		return repo.findByGroupName(groupName);
	}

}
