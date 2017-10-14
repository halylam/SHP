package vn.shp.portal.service;

import vn.shp.app.entity.PhanHe;

import java.util.List;

public interface PhanHeService {

	List<PhanHe> findAll();

	void save(PhanHe phanHe);
	
	PhanHe findOne(Long id);

	void delete(Long id);
	
	void delete(PhanHe entity);

	PhanHe findByPhanHeCode(String phanHeCode);
}
