package vn.shp.app.dao.impl;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.KhoaHocMonHoc;
import vn.shp.app.dao.KhoaHocMonHocDao;

import java.util.List;

/**
 * Created by hant1 on 16/10/2017.
 */
@Repository
public class KhoaHocMonHocDaoImpl extends GenericDAOImpl<KhoaHocMonHoc, Long> implements KhoaHocMonHocDao {
    @Override
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public List<KhoaHocMonHoc> searchByKhoaHocId(Long id) {
        Search s = new Search(KhoaHocMonHoc.class);
        s.addFilterEqual("khoaHoc.khoaHocId", id);
        return search(s);
    }
}
