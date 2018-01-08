package vn.shp.app.service;

import vn.shp.app.entity.HocVien;
import vn.shp.app.entity.KinhNghiemLamViec;

import java.util.List;

public interface HocVienService {

	List<HocVien> findAll();

	void save(HocVien entity);
	
	HocVien findOne(Long id);

	void delete(Long id);
	
	void delete(HocVien entity);

    HocVien findByMaHocVien(String maHocVien);
}
