package vn.shp.portal.service;

import vn.shp.app.entity.LopHoc;

import java.util.List;

public interface LopHocService {

	List<LopHoc> findAll();

	void save(LopHoc lopHoc);
	
	LopHoc findOne(Long id);

	void delete(Long id);
	
	void delete(LopHoc entity);

	LopHoc findByLopHocCode(String lopHocCode);
}
