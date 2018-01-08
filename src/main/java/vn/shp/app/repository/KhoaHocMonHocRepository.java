package vn.shp.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.KhoaHocMonHoc;

@Repository
public interface KhoaHocMonHocRepository extends JpaRepository<KhoaHocMonHoc, Long> {
	@Query("select u from KhoaHocMonHoc u where u.khoaHoc.khoaHocId like UPPER(CONCAT('%',:khoaHocId,'%'))")
	List<KhoaHocMonHoc> findBy(@Param("khoaHocId") Long khoaHocId);
}
