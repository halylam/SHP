package vn.shp.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.PortalGroup;

import java.util.List;

@Repository
public interface PortalGroupRepository extends JpaRepository<PortalGroup, Long> {

	List<PortalGroup> findByGroupNameContainingOrStatusContainingOrderByGroupIdDesc(String groupName, String status);
	
	List<PortalGroup> findByOrderByGroupIdDesc();

	PortalGroup findByGroupName(String groupName);

	PortalGroup findByGroupCode(String groupCode);

	@Query("select u from PortalGroup u where u.groupCode like UPPER(CONCAT('%',:groupCode,'%')) and u.groupName like UPPER(CONCAT('%',:groupName,'%')) and u.status like UPPER(CONCAT('%',:status,'%'))")
	List<PortalGroup> findBy(@Param("groupCode") String groupCode, @Param("groupName") String groupName, @Param("status") String status);

}
