package vn.shp.portal.service;

import vn.shp.app.entity.KhoaHoc;

import java.util.List;

public interface KhoaHocService {

	List<KhoaHoc> findAll();

	void save(KhoaHoc khoaHoc);
	
	KhoaHoc findOne(Long id);

	void delete(Long id);
	
	void delete(KhoaHoc entity);

	KhoaHoc findByKhoaHocCode(String khoaHocCode);
}
