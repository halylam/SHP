package vn.shp.portal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.ThoiKhoaBieu;

@Repository
public interface ThoiKhoaBieuRepository extends JpaRepository<ThoiKhoaBieu, Long> {

}
