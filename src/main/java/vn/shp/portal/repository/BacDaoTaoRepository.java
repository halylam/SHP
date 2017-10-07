package vn.shp.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.BacDaoTao;

import java.util.List;

@Repository
public interface BacDaoTaoRepository extends JpaRepository<BacDaoTao, Long> {

	@Query("select u from BacDaoTao u where u.bacDaoTaoName like UPPER(CONCAT('%',:bacDaoTaoName,'%')) and u.bacDaoTaoCode like UPPER(CONCAT('%',:bacDaoTaoCode,'%'))")
	Page<BacDaoTao> findBy(@Param("bacDaoTaoName") String bacDaoTaoName,
                             @Param("bacDaoTaoCode") String bacDaoTaoCode, Pageable pageable);

	@Query("select u from BacDaoTao u where u.bacDaoTaoName like UPPER(CONCAT('%',:bacDaoTaoName,'%')) and u.bacDaoTaoCode like UPPER(CONCAT('%',:bacDaoTaoCode,'%'))")
	List<BacDaoTao> findBy(@Param("bacDaoTaoName") String bacDaoTaoName,
                             @Param("bacDaoTaoCode") String bacDaoTaoCode);

	BacDaoTao findByBacDaoTaoCode(String bacDaoTaoCode);
}
