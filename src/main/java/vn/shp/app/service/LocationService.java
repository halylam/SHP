package vn.shp.app.service;

import vn.shp.app.entity.Location;

import java.util.List;

public interface LocationService {


    List<Location> findAllLocation(boolean sort);

    List<Location> findAllLocation(int level, boolean sort);

    List<Location> findLocation(String parentCode);
}

