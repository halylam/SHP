//package vn.vccb.portal.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import vn.shp.portal.entity.PortalWorkflow;
//import vn.shp.portal.repository.PortalWorkflowRepository;
//import vn.shp.portal.service.PortalWorkflowService;
//
//import java.util.List;
//
//@Service("PortalWorkflowService")
//public class PortalWorkflowServiceImpl implements PortalWorkflowService {
//
//	@Autowired
//	private PortalWorkflowRepository repo;
//
//	@Override
//	public List<PortalWorkflow> findAll() {
//		return repo.findAll();
//	}
//
//	@Override
//	@Transactional
//	public void save(PortalWorkflow entity) {
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
//	public PortalWorkflow findOne(Long id) {
//		return repo.findOne(id);
//	}
//
//	@Override
//	public PortalWorkflow findByCode(String code) {
//		return repo.findByCode(code);
//	}
//
//}
