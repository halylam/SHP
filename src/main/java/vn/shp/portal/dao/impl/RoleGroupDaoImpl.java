package vn.shp.portal.dao.impl;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.shp.app.entity.KhoaHoc;
import vn.shp.portal.dao.KhoaHocDao;
import vn.shp.portal.dao.RoleGroupDao;
import vn.shp.portal.entity.PortalGroup;

import java.util.Date;
import java.util.List;

@Repository
public class RoleGroupDaoImpl extends GenericDAOImpl<PortalGroup, Long> implements RoleGroupDao {
    @Override
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }


}