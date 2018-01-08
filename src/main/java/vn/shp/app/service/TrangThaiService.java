package vn.shp.app.service;

import vn.shp.app.entity.TrangThai;

import java.util.List;

public interface TrangThaiService {

	List<TrangThai> findAll();

	void save(TrangThai trangThai);
	
	TrangThai findOne(Long id);

	void delete(Long id);
	
	void delete(TrangThai entity);

	TrangThai findByTrangThaiCode(String trangThaiCode);
}
