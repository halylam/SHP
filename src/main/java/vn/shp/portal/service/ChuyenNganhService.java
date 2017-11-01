package vn.shp.portal.service;

import java.util.List;

import vn.shp.app.entity.ChuyenNganh;

public interface ChuyenNganhService {

	List<ChuyenNganh> findAll();

	void save(ChuyenNganh chuyenNganh);
	
	ChuyenNganh findOne(Long id);

	void delete(Long id);
	
	void delete(ChuyenNganh entity);

	ChuyenNganh findByChuyenNganhCode(String chuyenNganhCode);

	List<ChuyenNganh> searchByFilters(String chuyenNganhName, String chuyenNganhCode);
}
