package vn.shp.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.KinhNghiemLamViec;

import java.util.List;

@Repository
public interface KinhNghiemLamViecRepository extends JpaRepository<KinhNghiemLamViec, Long> {

    public List<KinhNghiemLamViec> findAllByMaLienKetAndLoaiLienKet(Long maLienKet, String loaiLienKet);


}
