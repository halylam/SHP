package vn.shp.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.shp.portal.entity.PortalTitle;

import java.util.List;

@Repository
public interface PortalTitleRepository extends JpaRepository<PortalTitle, Long> {

	List<PortalTitle> findByTitleCodeOrTitleNameOrStatus(String titleCode, String titleName, String status);

	PortalTitle findByTitleCode(String titleCode);

}
