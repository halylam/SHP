package vn.shp.portal.service;

import vn.shp.app.entity.CaHoc;

import java.util.List;

public interface CaHocService {

	List<CaHoc> findAll();

	void save(CaHoc caHoc);
	
	CaHoc findOne(Long id);

	void delete(Long id);
	
	void delete(CaHoc entity);

	CaHoc findByCaHocCode(String caHocCode);

	List<CaHoc> searchByFilters(String caHocName, String caHocCode);
}
