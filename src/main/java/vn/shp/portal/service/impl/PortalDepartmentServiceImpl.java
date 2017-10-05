package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.portal.entity.PortalDepartment;
import vn.shp.portal.repository.PortalDepartmentRepository;
import vn.shp.portal.service.PortalDepartmentService;

import java.util.List;

@Service("PortalDepartmentService")
public class PortalDepartmentServiceImpl implements PortalDepartmentService {

	@Autowired
	private PortalDepartmentRepository portalDepartmentRepo;
	
	@Override
	public List<PortalDepartment> findAll() {
		return portalDepartmentRepo.findAll();
	}

	@Override
	@Transactional
	public void save(PortalDepartment entity) {
		portalDepartmentRepo.save(entity);
	}
	
	@Override
	public List<PortalDepartment> findByData(PortalDepartment portalDepartment) {
		return portalDepartmentRepo.findByDepartmentCodeOrDepartmentNameOrStatus(portalDepartment.getDepartmentCode(), portalDepartment.getDepartmentName(), portalDepartment.getStatus());
	}
	
	@Override
	public PortalDepartment findOne(Long id) {
		return portalDepartmentRepo.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long departmentId) {
		portalDepartmentRepo.delete(departmentId);
	}

	@Override
	public PortalDepartment findByDepartmentCode(String departmentCode) {
		return portalDepartmentRepo.findByDepartmentCode(departmentCode);
	}

	@Override
	public PortalDepartment findByManager(String manager) {
		return portalDepartmentRepo.findByManager(manager);
	}

	@Override
	public PortalDepartment findByViceDirector(String viceDirector) {
		return portalDepartmentRepo.findByViceDirector(viceDirector);
	}

	@Override
	public PortalDepartment findBrnOrTso(String transCode) {

			return portalDepartmentRepo.findBrnOrTso(transCode);

	}

	@Override
	public List<PortalDepartment> findAllBrnOrTso() {
		return portalDepartmentRepo.findAllBrnOrTso();
	}

}
