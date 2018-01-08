package vn.shp.app.dao.impl;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.shp.app.dao.RoleGroupDao;
import vn.shp.app.entity.PortalGroup;

@Repository
public class RoleGroupDaoImpl extends GenericDAOImpl<PortalGroup, Long> implements RoleGroupDao {
    @Override
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }


}
