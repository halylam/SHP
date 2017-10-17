package vn.shp.portal.service;

import java.util.List;

import vn.shp.app.entity.LopHocHocVien;

public interface LopHocHocVienService {

	List<LopHocHocVien> findAll();

	void save(LopHocHocVien lopHocHocVien);
	
	LopHocHocVien findOne(Long id);

	void delete(Long id);
	
	void delete(LopHocHocVien entity);

	List<LopHocHocVien> findByLopHocId(Long lopHocId);
}
