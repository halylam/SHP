package vn.shp.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.dao.LocationDao;
import vn.shp.app.entity.Location;
import vn.shp.app.service.LocationService;

import java.util.List;

@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationDao locationDao;

    @Override
    public List<Location> findAllLocation(boolean sort) {
        return locationDao.findAllLocation(sort);
    }

    @Override
    public List<Location> findAllLocation(int level, boolean sort) {
        return locationDao.findAllLocation(level, sort);
    }

    @Override
    public List<Location> findLocation(String parentCode) {
        return locationDao.findLocation(parentCode);
    }

}

