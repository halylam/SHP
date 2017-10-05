package vn.shp.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.portal.entity.PortalConfig;

@Repository
public interface PortalConfigRepository extends JpaRepository<PortalConfig, Long> {

	@Query("select u from PortalConfig u where u.configName = :configName and u.system = :system and u.enable = 'Y'")
	PortalConfig find(@Param("system") String system, @Param("configName") String configName);

}
