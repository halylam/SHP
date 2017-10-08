package vn.shp.portal.service;

import vn.shp.app.entity.HocVien;

import java.util.List;

public interface HocVienService {

	List<HocVien> findAll();

	void save(HocVien entity);
	
	HocVien findOne(Long id);

	void delete(Long id);
	
	void delete(HocVien entity);

}
