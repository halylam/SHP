package vn.shp.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.HocVienDk;

@Repository
public interface HocVienDkRepository extends JpaRepository<HocVienDk, Long> {


}
