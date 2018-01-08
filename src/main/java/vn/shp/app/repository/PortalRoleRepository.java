package vn.shp.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.PortalRole;

import java.util.List;

@Repository
public interface PortalRoleRepository extends JpaRepository<PortalRole, Long> {

	@Query("select u from PortalRole u where u.roleCode like UPPER(CONCAT('%',:roleCode,'%')) and u.roleName like UPPER(CONCAT('%',:roleName,'%')) and u.status like UPPER(CONCAT('%',:status,'%'))")
	Page<PortalRole> findBy(@Param("roleCode") String roleCode, @Param("roleName") String roleName, @Param("status") String status, Pageable pageable);

	@Query("select u from PortalRole u where u.roleCode like UPPER(CONCAT('%',:roleCode,'%')) and u.roleName like UPPER(CONCAT('%',:roleName,'%')) and u.status like UPPER(CONCAT('%',:status,'%'))")
	List<PortalRole> findBy(@Param("roleCode") String roleCode, @Param("roleName") String roleName, @Param("status") String status);

	PortalRole findByRoleCode(String roleCode);
}
