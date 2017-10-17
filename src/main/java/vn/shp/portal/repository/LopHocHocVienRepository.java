package vn.shp.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.LopHocHocVien;

@Repository
public interface LopHocHocVienRepository extends JpaRepository<LopHocHocVien, Long> {

}
