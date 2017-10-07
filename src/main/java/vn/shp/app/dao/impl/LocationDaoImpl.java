package vn.shp.app.dao.impl;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.shp.app.dao.LocationDao;
import vn.shp.app.entity.Location;

import java.util.List;

@Repository
public class LocationDaoImpl extends GenericDAOImpl<Location, Long> implements LocationDao {
    @Override
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public List<Location> findAllLocation(boolean sort) {
        String hql = "from Location where recordStat = :recordStat and authStat = :authStat ";
        if (sort) {
            hql += "order by seq, locName";
        }
        Query query = getSession().createQuery(hql);
        query.setParameter("recordStat", "O");
        query.setParameter("authStat", "A");
        return query.list();
    }

    @Override
    public List<Location> findAllLocation(int level, boolean sort) {

        String hql = "from Location where recordStat = :recordStat and authStat = :authStat ";
        if(level == 0){
            hql += "and length(locCode) = 3";
        }else if(level == 1){
            hql += "and length(locCode) = 6";
        }else  if(level == 2){
            hql += "and length(locCode) = 9";
        }
        if (sort) {
            hql += "order by seq,locName";
        }

        Query query = getSession().createQuery(hql);
        query.setParameter("recordStat", "O");
        query.setParameter("authStat", "A");

        return query.list();
    }

    @Override
    public List<Location> findLocation(String parentCode) {
        String hql = "from Location where recordStat = :recordStat and authStat = :authStat ";
        if(parentCode != null){
            hql += " and parentCode =:parentCode";
        }
        hql += " order by locName";

        Query query = getSession().createQuery(hql);
        query.setParameter("recordStat", "O");
        query.setParameter("authStat", "A");
        if(parentCode != null){
            query.setParameter("parentCode", parentCode);
        }

        return query.list();
    }

}

