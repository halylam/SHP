package vn.shp.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.PhanHe;

import java.util.List;

@Repository
public interface PhanHeRepository extends JpaRepository<PhanHe, Long> {

	@Query("select u from PhanHe u where u.phanHeName like UPPER(CONCAT('%',:phanHeName,'%')) and u.phanHeCode like UPPER(CONCAT('%',:phanHeCode,'%'))")
	Page<PhanHe> findBy(@Param("phanHeName") String phanHeName,
                       @Param("phanHeCode") String phanHeCode, Pageable pageable);

	@Query("select u from PhanHe u where u.phanHeName like UPPER(CONCAT('%',:phanHeName,'%')) and u.phanHeCode like UPPER(CONCAT('%',:phanHeCode,'%'))")
	List<PhanHe> findBy(@Param("phanHeName") String phanHeName,
                       @Param("phanHeCode") String phanHeCode);

	PhanHe findByPhanHeCode(String phanHeCode);
}
