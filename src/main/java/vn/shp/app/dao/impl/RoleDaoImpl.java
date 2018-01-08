package vn.shp.app.dao.impl;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.shp.app.dao.RoleDao;
import vn.shp.app.entity.PortalRole;

@Repository
public class RoleDaoImpl extends GenericDAOImpl<PortalRole, Long> implements RoleDao {
    @Override
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }


}
