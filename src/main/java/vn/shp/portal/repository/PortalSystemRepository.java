//package vn.shp.portal.repository;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import vn.shp.portal.entity.PortalSystem;
//
//import java.util.List;
//
//@Repository
//public interface PortalSystemRepository extends JpaRepository<PortalSystem, Long> {
//
//	@Query("select u from PortalSystem u where u.systemCode like UPPER(CONCAT('%',:systemCode,'%')) and u.systemName like UPPER(CONCAT('%',:systemName,'%')) and u.status like UPPER(CONCAT('%',:status,'%'))")
//	Page<PortalSystem> findBy(@Param("systemCode") String systemCode, @Param("systemName") String systemName, @Param("status") String status, Pageable pageable);
//
//	@Query("select u from PortalSystem u where u.systemCode like UPPER(CONCAT('%',:systemCode,'%')) and u.systemName like UPPER(CONCAT('%',:systemName,'%')) and u.status like UPPER(CONCAT('%',:status,'%'))")
//	List<PortalSystem> findBy(@Param("systemCode") String systemCode, @Param("systemName") String systemName, @Param("status") String status);
//
//}
