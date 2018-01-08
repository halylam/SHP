package vn.shp.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.ChuongTrinhDaoTao;

@Repository
public interface ChuongTrinhDaoTaoRepository extends JpaRepository<ChuongTrinhDaoTao, Long> {

	@Query("select u from ChuongTrinhDaoTao u where u.chuongTrinhDaoTaoName like UPPER(CONCAT('%',:chuongTrinhDaoTaoName,'%')) and u.chuongTrinhDaoTaoCode like UPPER(CONCAT('%',:chuongTrinhDaoTaoCode,'%'))")
	Page<ChuongTrinhDaoTao> findBy(@Param("chuongTrinhDaoTaoName") String chuongTrinhDaoTaoName,
								   @Param("chuongTrinhDaoTaoCode") String chuongTrinhDaoTaoCode, Pageable pageable);

	@Query("select u from ChuongTrinhDaoTao u where u.chuongTrinhDaoTaoName like UPPER(CONCAT('%',:chuongTrinhDaoTaoName,'%')) and u.chuongTrinhDaoTaoCode like UPPER(CONCAT('%',:chuongTrinhDaoTaoCode,'%'))")
	List<ChuongTrinhDaoTao> findBy(@Param("chuongTrinhDaoTaoName") String chuongTrinhDaoTaoName,
								   @Param("chuongTrinhDaoTaoCode") String chuongTrinhDaoTaoCode);

	ChuongTrinhDaoTao findByChuongTrinhDaoTaoCode(String chuongTrinhDaoTaoCode);

}
