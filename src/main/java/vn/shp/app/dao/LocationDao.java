package vn.shp.app.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import vn.shp.app.entity.Location;

import java.util.List;

public interface LocationDao extends GenericDAO<Location, Long> {


    List<Location> findAllLocation(boolean sort);

    List<Location> findAllLocation(int level, boolean sort);

    List<Location> findLocation(String parentCode);
}

