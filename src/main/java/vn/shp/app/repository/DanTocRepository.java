package vn.shp.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.DanToc;

import java.util.List;

@Repository
public interface DanTocRepository extends JpaRepository<DanToc, Long> {

	@Query("select u from DanToc u where u.danTocName like UPPER(CONCAT('%',:danTocName,'%')) and u.danTocCode like UPPER(CONCAT('%',:danTocCode,'%'))")
	Page<DanToc> findBy(@Param("danTocName") String danTocName,
                       @Param("danTocCode") String danTocCode, Pageable pageable);

	@Query("select u from DanToc u where u.danTocName like UPPER(CONCAT('%',:danTocName,'%')) and u.danTocCode like UPPER(CONCAT('%',:danTocCode,'%'))")
	List<DanToc> findBy(@Param("danTocName") String danTocName,
                       @Param("danTocCode") String danTocCode);

	DanToc findByDanTocCode(String danTocCode);
}
