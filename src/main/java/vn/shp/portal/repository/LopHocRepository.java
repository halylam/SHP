package vn.shp.portal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.LopHoc;

@Repository
public interface LopHocRepository extends JpaRepository<LopHoc, Long> {

	@Query("select u from LopHoc u where u.lopHocName like UPPER(CONCAT('%',:lopHocName,'%')) and u.lopHocCode like UPPER(CONCAT('%',:lopHocCode,'%'))")
	Page<LopHoc> findBy(@Param("lopHocName") String lopHocName,
							@Param("lopHocCode") String lopHocCode, Pageable pageable);

	@Query("select u from LopHoc u where u.lopHocName like UPPER(CONCAT('%',:lopHocName,'%')) and u.lopHocCode like UPPER(CONCAT('%',:lopHocCode,'%'))")
	List<LopHoc> findBy(@Param("lopHocName") String lopHocName,
							@Param("lopHocCode") String lopHocCode);

	LopHoc findByLopHocCode(String lopHocCode);
}
