package vn.shp.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.ChuyenMon;

import java.util.List;

@Repository
public interface ChuyenMonRepository extends JpaRepository<ChuyenMon, Long> {

	@Query("select u from ChuyenMon u where u.chuyenMonName like UPPER(CONCAT('%',:chuyenMonName,'%')) and u.chuyenMonCode like UPPER(CONCAT('%',:chuyenMonCode,'%'))")
	Page<ChuyenMon> findBy(@Param("chuyenMonName") String chuyenMonName,
                        @Param("chuyenMonCode") String chuyenMonCode, Pageable pageable);

	@Query("select u from ChuyenMon u where u.chuyenMonName like UPPER(CONCAT('%',:chuyenMonName,'%')) and u.chuyenMonCode like UPPER(CONCAT('%',:chuyenMonCode,'%'))")
	List<ChuyenMon> findBy(@Param("chuyenMonName") String chuyenMonName,
                        @Param("chuyenMonCode") String chuyenMonCode);

	ChuyenMon findByChuyenMonCode(String chuyenMonCode);
}
