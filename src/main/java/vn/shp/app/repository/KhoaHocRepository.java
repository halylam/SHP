package vn.shp.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.KhoaHoc;

import java.util.List;

@Repository
public interface KhoaHocRepository extends JpaRepository<KhoaHoc, Long> {

	@Query("select u from KhoaHoc u where u.khoaHocName like UPPER(CONCAT('%',:khoaHocName,'%')) and u.khoaHocCode like UPPER(CONCAT('%',:khoaHocCode,'%'))")
	Page<KhoaHoc> findBy(@Param("khoaHocName") String khoaHocName,
                        @Param("khoaHocCode") String khoaHocCode, Pageable pageable);

	@Query("select u from KhoaHoc u where u.khoaHocName like UPPER(CONCAT('%',:khoaHocName,'%')) and u.khoaHocCode like UPPER(CONCAT('%',:khoaHocCode,'%'))")
	List<KhoaHoc> findBy(@Param("khoaHocName") String khoaHocName,
                        @Param("khoaHocCode") String khoaHocCode);

	KhoaHoc findByKhoaHocCode(String khoaHocCode);


}
