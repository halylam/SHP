package vn.shp.portal.service;

import vn.shp.app.entity.BoMon;

import java.util.List;

public interface BoMonService {

	List<BoMon> findAll();

	void save(BoMon boMon);
	
	BoMon findOne(Long id);

	void delete(Long id);
	
	void delete(BoMon entity);

	BoMon findByBoMonCode(String boMonCode);

	List<BoMon> searchByFilters(String boMonName, String boMonCode);
}
