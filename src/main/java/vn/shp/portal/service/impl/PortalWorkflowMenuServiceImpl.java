//package vn.vccb.portal.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import vn.shp.portal.entity.PortalWorkflowMenu;
//import vn.shp.portal.repository.PortalWorkflowMenuRepository;
//import vn.shp.portal.service.PortalWorkflowMenuService;
//
//import java.util.List;
//
//@Service("PortalWorkflowMenuService")
//public class PortalWorkflowMenuServiceImpl implements PortalWorkflowMenuService {
//
//	@Autowired
//	private PortalWorkflowMenuRepository repo;
//
//	@Override
//	public List<PortalWorkflowMenu> findAll() {
//		return repo.findAll();
//	}
//
//	@Override
//	@Transactional
//	public void save(PortalWorkflowMenu entity) {
//		repo.save(entity);
//	}
//
//	@Override
//	@Transactional
//	public void delete(Long id) {
//		repo.delete(id);
//	}
//
//	@Override
//	public PortalWorkflowMenu findOne(Long id) {
//		return repo.findOne(id);
//	}
//
//	@Override
//	public List<PortalWorkflowMenu> findAllByOrderByRemarkAsc() {
//		return repo.findAllByOrderByRemarkAsc();
//	}
//
//}
