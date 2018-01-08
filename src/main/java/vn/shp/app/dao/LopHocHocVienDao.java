package vn.shp.app.dao;

import java.util.List;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import vn.shp.app.entity.LopHocHocVien;

/**
 * Created by hant1 on 16/10/2017.
 */
public interface LopHocHocVienDao extends GenericDAO<LopHocHocVien, Long> {
    List<LopHocHocVien> searchByLopHocId(Long id);
}
