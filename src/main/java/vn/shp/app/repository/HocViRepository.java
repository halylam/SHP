package vn.shp.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.HocVi;

import java.util.List;

@Repository
public interface HocViRepository extends JpaRepository<HocVi, Long> {

	@Query("select u from HocVi u where u.hocViName like UPPER(CONCAT('%',:hocViName,'%')) and u.hocViCode like UPPER(CONCAT('%',:hocViCode,'%'))")
	Page<HocVi> findBy(@Param("hocViName") String hocViName,
                        @Param("hocViCode") String hocViCode, Pageable pageable);

	@Query("select u from HocVi u where u.hocViName like UPPER(CONCAT('%',:hocViName,'%')) and u.hocViCode like UPPER(CONCAT('%',:hocViCode,'%'))")
	List<HocVi> findBy(@Param("hocViName") String hocViName,
                        @Param("hocViCode") String hocViCode);

	HocVi findByHocViCode(String hocViCode);
}
