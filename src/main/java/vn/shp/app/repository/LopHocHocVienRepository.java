package vn.shp.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.LopHocHocVien;

@Repository
public interface LopHocHocVienRepository extends JpaRepository<LopHocHocVien, Long> {

}
