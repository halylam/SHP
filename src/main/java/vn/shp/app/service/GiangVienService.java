package vn.shp.app.service;

import vn.shp.app.entity.GiangVien;

import java.util.List;

public interface GiangVienService {

	List<GiangVien> findAll();

	void save(GiangVien entity);
	
	GiangVien findOne(Long id);

	void delete(Long id);
	
	void delete(GiangVien entity);

	List<GiangVien> findByThuAndCaHoc(String thu, String caHocCode);

}
