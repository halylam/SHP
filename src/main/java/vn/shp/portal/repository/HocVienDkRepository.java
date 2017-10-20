package vn.shp.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.HocVienDk;

import java.util.List;

@Repository
public interface HocVienDkRepository extends JpaRepository<HocVienDk, Long> {

    @Query("select u from HocVienDk u where u.hocVien.maHocVien like UPPER(:maHocVien)")
    List<HocVienDk> findByMaHocVien(@Param("maHocVien") String maHocVien);

    @Query("select u from HocVienDk u where u.khoaHoc.khoaHocCode like UPPER(:maKhoaHoc)")
    List<HocVienDk> findByMaKhoaHoc(@Param("maKhoaHoc") String maKhoaHoc);

    @Query("select u from HocVienDk u where u.khoaHoc.khoaHocId =:khoaHocId")
    List<HocVienDk> findByKhoaHocId(@Param("khoaHocId") Long khoaHocId);

}
