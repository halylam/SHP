package vn.shp.portal.dao;

import java.util.List;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import vn.shp.app.entity.HocVienDk;
import vn.shp.app.entity.KhoaHoc;
import vn.shp.portal.filter.HocVienDkFilter;

public interface HocVienDkDao extends GenericDAO<HocVienDk, Long> {
    List<HocVienDk> searchByFilters(HocVienDkFilter filter);
}
