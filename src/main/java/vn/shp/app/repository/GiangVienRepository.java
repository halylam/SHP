package vn.shp.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.GiangVien;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, Long> {

}
