package vn.shp.portal.service;

import java.util.List;

import vn.shp.app.entity.KhoaHocMonHoc;

public interface KhoaHocMonHocService {

	List<KhoaHocMonHoc> findAll();

	void save(KhoaHocMonHoc khoaHocMonHoc);
	
	KhoaHocMonHoc findOne(Long id);

	void delete(Long id);
	
	void delete(KhoaHocMonHoc entity);

	List<KhoaHocMonHoc> findByKhoaHocId(Long khoaHocId);
}
