package vn.shp.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.TrangThai;

import java.util.List;

@Repository
public interface TrangThaiRepository extends JpaRepository<TrangThai, Long> {

	@Query("select u from TrangThai u where u.trangThaiName like UPPER(CONCAT('%',:trangThaiName,'%')) and u.trangThaiCode like UPPER(CONCAT('%',:trangThaiCode,'%'))")
	Page<TrangThai> findBy(@Param("trangThaiName") String trangThaiName,
                       @Param("trangThaiCode") String trangThaiCode, Pageable pageable);

	@Query("select u from TrangThai u where u.trangThaiName like UPPER(CONCAT('%',:trangThaiName,'%')) and u.trangThaiCode like UPPER(CONCAT('%',:trangThaiCode,'%'))")
	List<TrangThai> findBy(@Param("trangThaiName") String trangThaiName,
                       @Param("trangThaiCode") String trangThaiCode);

	TrangThai findByTrangThaiCode(String trangThaiCode);
}
