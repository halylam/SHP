package vn.shp.portal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.LoaiLopHoc;

@Repository
public interface LoaiLopHocRepository extends JpaRepository<LoaiLopHoc, Long> {

	@Query("select u from LoaiLopHoc u where u.loaiLopHocName like UPPER(CONCAT('%',:loaiLopHocName,'%')) and u.loaiLopHocCode like UPPER(CONCAT('%',:loaiLopHocCode,'%'))")
	Page<LoaiLopHoc> findBy(@Param("loaiLopHocName") String loaiLopHocName,
					   @Param("loaiLopHocCode") String loaiLopHocCode, Pageable pageable);

	@Query("select u from LoaiLopHoc u where u.loaiLopHocName like UPPER(CONCAT('%',:loaiLopHocName,'%')) and u.loaiLopHocCode like UPPER(CONCAT('%',:loaiLopHocCode,'%'))")
	List<LoaiLopHoc> findBy(@Param("loaiLopHocName") String loaiLopHocName,
					   @Param("loaiLopHocCode") String loaiLopHocCode);

	LoaiLopHoc findByLoaiLopHocCode(String loaiLopHocCode);
}
