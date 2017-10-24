package vn.shp.portal.service;

import java.util.List;

import vn.shp.app.entity.LopHoc;

public interface LopHocService {

	List<LopHoc> findAll();

	void save(LopHoc lopHoc);
	
	LopHoc findOne(Long id);

	void delete(Long id);
	
	void delete(LopHoc entity);

	LopHoc findByLopHocCode(String lopHocCode);

	List<LopHoc> searchByFilters(String lopHocName, String lopHocCode);
}
