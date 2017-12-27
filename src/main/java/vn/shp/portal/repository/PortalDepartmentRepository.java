//package vn.shp.portal.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import vn.shp.portal.entity.PortalDepartment;
//
//import java.util.List;
//
//@Repository
//public interface PortalDepartmentRepository extends JpaRepository<PortalDepartment, Long> {
//
//	List<PortalDepartment> findByDepartmentCodeOrDepartmentNameOrStatus(String departmentCode, String departmentName,
//			String status);
//
//	PortalDepartment findByDepartmentCode(String departmentCode);
//
//	PortalDepartment findByManager(String manager);
//
//	PortalDepartment findByViceDirector(String viceDirector);
//
//	@Query("select u from PortalDepartment u where u.transCode = :transCode and u.departmentType in ('BRN','TSO')")
//	PortalDepartment findBrnOrTso(@Param("transCode") String transCode);
//
//	@Query("select u from PortalDepartment u where u.departmentType in ('BRN','TSO')")
//	List<PortalDepartment> findAllBrnOrTso();
//
//	@Query("select u from PortalDepartment u where u.departmentType in ('BRN','TSO') and (u.parentCode = :parentTransCode or u.transCode = :parentTransCode)")
//	List<PortalDepartment> findAllBrnOrTso(@Param("parentTransCode") String parentTransCode);
//}
