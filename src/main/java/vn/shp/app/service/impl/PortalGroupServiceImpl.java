package vn.shp.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.PortalGroup;
import vn.shp.app.repository.PortalGroupRepository;
import vn.shp.app.service.PortalGroupService;

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

	@Override
	public PortalGroup findByGroupCode(String groupCode) {
		return repo.findByGroupCode(groupCode);
	}

	@Override
	public List<PortalGroup> searchByFilters(String groupCode, String groupName) {
		return repo.findBy(groupCode, groupName, "O");
	}

}
