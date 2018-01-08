package vn.shp.app.dao.impl;

import java.util.List;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.HocVienDk;
import vn.shp.app.dao.HocVienDkDao;
import vn.shp.app.filter.HocVienDkFilter;

@Repository
public class HocVienDkDaoImpl extends GenericDAOImpl<HocVienDk, Long> implements HocVienDkDao {
    @Override
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public List<HocVienDk> searchByFilters(HocVienDkFilter filter) {
        Search s = new Search(HocVienDk.class);
        s.addFilterGreaterOrEqual("ngayTao", filter.getFromDate());
        s.addFilterLessOrEqual("ngayTao", filter.getToDate());
        s.addFilterEqual("khoaHoc.khoaHocId", filter.getKhoaHocId());
        s.addFilterEqual("khoaHoc.bacDaoTao.chuyenNganh.chuyenNganhId", filter.getChuyenNganhId());
        s.addFilterEqual("khoaHoc.bacDaoTao.chuyenNganh.chuongTrinhDaoTao.chuongTrinhDaoTaoId", filter.getCtdtId());
        return search(s);
    }

}
