package vn.shp.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.ThoiKhoaBieu;

@Repository
public interface ThoiKhoaBieuRepository extends JpaRepository<ThoiKhoaBieu, Long> {

}
