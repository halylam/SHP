package vn.shp.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.HocVien;

@Repository
public interface HocVienRepository extends JpaRepository<HocVien, Long> {

}
