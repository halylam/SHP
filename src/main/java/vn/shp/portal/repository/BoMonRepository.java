package vn.shp.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.BoMon;

import java.util.List;

@Repository
public interface BoMonRepository extends JpaRepository<BoMon, Long> {

	@Query("select u from BoMon u where u.boMonName like UPPER(CONCAT('%',:boMonName,'%')) and u.boMonCode like UPPER(CONCAT('%',:boMonCode,'%'))")
	Page<BoMon> findBy(@Param("boMonName") String boMonName,
                           @Param("boMonCode") String boMonCode, Pageable pageable);

	@Query("select u from BoMon u where u.boMonName like UPPER(CONCAT('%',:boMonName,'%')) and u.boMonCode like UPPER(CONCAT('%',:boMonCode,'%'))")
	List<BoMon> findBy(@Param("boMonName") String boMonName,
                           @Param("boMonCode") String boMonCode);

	BoMon findByBoMonCode(String boMonCode);
}
