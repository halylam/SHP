package vn.shp.portal.service;

import java.util.List;

import vn.shp.app.entity.LoaiLopHoc;

public interface LoaiLopHocService {

	List<LoaiLopHoc> findAll();

	void save(LoaiLopHoc loaiLopHoc);
	
	LoaiLopHoc findOne(Long id);

	void delete(Long id);
	
	void delete(LoaiLopHoc entity);

	LoaiLopHoc findByLoaiLopHocCode(String loaiLopHocCode);
}
