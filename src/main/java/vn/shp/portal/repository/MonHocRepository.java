package vn.shp.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.MonHoc;

import java.util.List;

@Repository
public interface MonHocRepository extends JpaRepository<MonHoc, Long> {

	@Query("select u from MonHoc u where u.monHocName like UPPER(CONCAT('%',:monHocName,'%')) and u.monHocCode like UPPER(CONCAT('%',:monHocCode,'%'))")
	Page<MonHoc> findBy(@Param("monHocName") String monHocName,
                       @Param("monHocCode") String monHocCode, Pageable pageable);

	@Query("select u from MonHoc u where u.monHocName like UPPER(CONCAT('%',:monHocName,'%')) and u.monHocCode like UPPER(CONCAT('%',:monHocCode,'%'))")
	List<MonHoc> findBy(@Param("monHocName") String monHocName,
                       @Param("monHocCode") String monHocCode);

	MonHoc findByMonHocCode(String monHocCode);
}
