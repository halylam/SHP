package vn.shp.app.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import vn.shp.app.entity.KhoaHoc;

import java.util.List;

public interface KhoaHocDao  extends GenericDAO<KhoaHoc, Long> {
    List<KhoaHoc> findKhoaHocDangKy();
}
