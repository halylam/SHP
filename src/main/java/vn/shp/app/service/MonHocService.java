package vn.shp.app.service;

import vn.shp.app.entity.MonHoc;

import java.util.List;

public interface MonHocService {

	List<MonHoc> findAll();

	void save(MonHoc monHoc);
	
	MonHoc findOne(Long id);

	void delete(Long id);
	
	void delete(MonHoc entity);

	MonHoc findByMonHocCode(String monHocCode);

	List<MonHoc> searchByFilters(String monHocName, String monHocCode);
}
