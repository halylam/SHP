package vn.shp.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.CaHoc;

import java.util.List;

@Repository
public interface CaHocRepository extends JpaRepository<CaHoc, Long> {

	@Query("select u from CaHoc u where u.caHocName like UPPER(CONCAT('%',:caHocName,'%')) and u.caHocCode like UPPER(CONCAT('%',:caHocCode,'%'))")
	Page<CaHoc> findBy(@Param("caHocName") String caHocName,
                        @Param("caHocCode") String caHocCode, Pageable pageable);

	@Query("select u from CaHoc u where u.caHocName like UPPER(CONCAT('%',:caHocName,'%')) and u.caHocCode like UPPER(CONCAT('%',:caHocCode,'%'))")
	List<CaHoc> findBy(@Param("caHocName") String caHocName,
                        @Param("caHocCode") String caHocCode);

	CaHoc findByCaHocCode(String caHocCode);
}
