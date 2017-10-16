package vn.shp.portal.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import vn.shp.app.entity.KhoaHocMonHoc;

import java.util.List;

/**
 * Created by hant1 on 16/10/2017.
 */
public interface KhoaHocMonHocDao extends GenericDAO<KhoaHocMonHoc, Long> {
    List<KhoaHocMonHoc> searchByKhoaHocId(Long id);
}
