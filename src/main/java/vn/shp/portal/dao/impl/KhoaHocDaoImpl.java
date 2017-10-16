package vn.shp.portal.dao.impl;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.KhoaHoc;
import vn.shp.portal.dao.KhoaHocDao;

import java.util.Date;
import java.util.List;

@Repository
public class KhoaHocDaoImpl extends GenericDAOImpl<KhoaHoc, Long> implements KhoaHocDao {
    @Override
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public List<KhoaHoc> findKhoaHocDangKy() {
        Search s = new Search(KhoaHoc.class);
        s.addFilterGreaterThan("timeTo", new Date());
        s.addFilterEqual("trangThai", true);
        return search(s);
    }
}
