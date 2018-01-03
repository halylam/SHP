//package vn.shp.portal.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import vn.shp.portal.entity.PortalSystemGroup;
//import vn.shp.portal.repository.PortalSystemGroupRepository;
//import vn.shp.portal.service.PortalSystemGroupService;
//
//import java.util.List;
//
//@Service("PortalSystemGroupService")
//public class PortalSystemGroupServiceImpl implements PortalSystemGroupService {
//
//	@Autowired
//	private PortalSystemGroupRepository portalGroupRepo;
//
//	@Override
//	public List<PortalSystemGroup> findAll() {
//		return portalGroupRepo.findAll();
//	}
//
//	@Override
//	@Transactional
//	public void save(PortalSystemGroup entity) {
//		portalGroupRepo.save(entity);
//	}
//
//	@Override
//	public List<PortalSystemGroup> findByData(PortalSystemGroup portalGroup) {
//		return portalGroupRepo.findByGroupCodeOrGroupNameOrStatus(portalGroup.getGroupCode(), portalGroup.getGroupName(), portalGroup.getStatus());
//	}
//
//	@Override
//	public PortalSystemGroup findOne(Long id) {
//		return portalGroupRepo.findOne(id);
//	}
//
//	@Override
//	@Transactional
//	public void delete(Long groupId) {
//		portalGroupRepo.delete(groupId);
//	}
//
//	@Override
//	public PortalSystemGroup findByGroupName(String groupName) {
//		return portalGroupRepo.findByGroupName(groupName);
//	}
//
//}
