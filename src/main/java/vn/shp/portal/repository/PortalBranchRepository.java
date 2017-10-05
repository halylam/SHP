package vn.shp.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.portal.entity.PortalBranch;

import java.util.List;

@Repository
public interface PortalBranchRepository extends JpaRepository<PortalBranch, Long> {

	@Query("select u from PortalBranch u where u.branchName like UPPER(CONCAT('%',:branchName,'%')) and u.branchCode like UPPER(CONCAT('%',:branchCode,'%'))")
	Page<PortalBranch> findBy(@Param("branchName") String branchName, @Param("branchCode") String branchCode, Pageable pageable);

	@Query("select u from PortalBranch u where u.branchName like UPPER(CONCAT('%',:branchName,'%')) and u.branchCode like UPPER(CONCAT('%',:branchCode,'%'))")
	List<PortalBranch> findBy(@Param("branchName") String branchName, @Param("branchCode") String branchCode);

	PortalBranch findByBranchCode(String branchCode);

}
