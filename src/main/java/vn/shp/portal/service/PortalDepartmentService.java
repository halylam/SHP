package vn.shp.portal.service;

import vn.shp.portal.entity.PortalDepartment;

import java.util.List;

public interface PortalDepartmentService {

	void save(PortalDepartment entity);

	List<PortalDepartment> findAll();

	List<PortalDepartment> findByData(PortalDepartment portalDepartment);

	PortalDepartment findOne(Long id);

	void delete(Long departmentId);

	PortalDepartment findByDepartmentCode(String departmentCode);

	PortalDepartment findByManager(String manager);

	PortalDepartment findByViceDirector(String viceDirector);

	PortalDepartment findBrnOrTso(String transCode);

	List<PortalDepartment> findAllBrnOrTso();
}
