package vn.shp.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.shp.portal.entity.PortalGroup;

import java.util.List;

@Repository
public interface PortalGroupRepository extends JpaRepository<PortalGroup, Long> {

	List<PortalGroup> findByGroupNameContainingOrStatusContainingOrderByGroupIdDesc(String groupName, String status);
	
	List<PortalGroup> findByOrderByGroupIdDesc();

	PortalGroup findByGroupName(String groupName);

}
