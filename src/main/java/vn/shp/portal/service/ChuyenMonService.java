package vn.shp.portal.service;

import vn.shp.app.entity.ChuyenMon;

import java.util.List;

public interface ChuyenMonService {

	List<ChuyenMon> findAll();

	void save(ChuyenMon boMon);
	
	ChuyenMon findOne(Long id);

	void delete(Long id);
	
	void delete(ChuyenMon entity);

	ChuyenMon findByChuyenMonCode(String boMonCode);
}
