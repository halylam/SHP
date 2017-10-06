package vn.shp.portal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.ChuyenNganh;

@Repository
public interface ChuyenNganhRepository extends JpaRepository<ChuyenNganh, Long> {

	@Query("select u from ChuyenNganh u where u.chuyenNganhName like UPPER(CONCAT('%',:chuyenNganhName,'%')) and u.chuyenNganhCode like UPPER(CONCAT('%',:chuyenNganhCode,'%'))")
	Page<ChuyenNganh> findBy(@Param("chuyenNganhName") String chuyenNganhName,
							 @Param("chuyenNganhCode") String chuyenNganhCode, Pageable pageable);

	@Query("select u from ChuyenNganh u where u.chuyenNganhName like UPPER(CONCAT('%',:chuyenNganhName,'%')) and u.chuyenNganhCode like UPPER(CONCAT('%',:chuyenNganhCode,'%'))")
	List<ChuyenNganh> findBy(@Param("chuyenNganhName") String chuyenNganhName,
							 @Param("chuyenNganhCode") String chuyenNganhCode);

	ChuyenNganh findByChuyenNganhCode(String chuyenNganhCode);
}
