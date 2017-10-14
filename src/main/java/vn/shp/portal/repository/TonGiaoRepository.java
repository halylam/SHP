package vn.shp.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.TonGiao;

import java.util.List;

@Repository
public interface TonGiaoRepository extends JpaRepository<TonGiao, Long> {

	@Query("select u from TonGiao u where u.tonGiaoName like UPPER(CONCAT('%',:tonGiaoName,'%')) and u.tonGiaoCode like UPPER(CONCAT('%',:tonGiaoCode,'%'))")
	Page<TonGiao> findBy(@Param("tonGiaoName") String tonGiaoName,
                        @Param("tonGiaoCode") String tonGiaoCode, Pageable pageable);

	@Query("select u from TonGiao u where u.tonGiaoName like UPPER(CONCAT('%',:tonGiaoName,'%')) and u.tonGiaoCode like UPPER(CONCAT('%',:tonGiaoCode,'%'))")
	List<TonGiao> findBy(@Param("tonGiaoName") String tonGiaoName,
                        @Param("tonGiaoCode") String tonGiaoCode);

	TonGiao findByTonGiaoCode(String tonGiaoCode);
}
