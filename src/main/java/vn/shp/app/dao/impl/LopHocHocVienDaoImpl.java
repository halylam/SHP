package vn.shp.app.dao.impl;

import java.util.List;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.LopHocHocVien;
import vn.shp.app.dao.LopHocHocVienDao;

/**
 * Created by hant1 on 16/10/2017.
 */
@Repository
public class LopHocHocVienDaoImpl extends GenericDAOImpl<LopHocHocVien, Long> implements LopHocHocVienDao {
    @Override
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public List<LopHocHocVien> searchByLopHocId(Long id) {
        Search s = new Search(LopHocHocVien.class);
        s.addFilterEqual("lopHoc.lopHocId", id);
        return search(s);
    }
}
