package vn.shp.portal.service;

import vn.shp.app.entity.HocVien;
import vn.shp.app.entity.KinhNghiemLamViec;

import java.util.List;

public interface HocVienService {

	List<HocVien> findAll();

	void save(HocVien entity);
	
	HocVien findOne(Long id);

	void delete(Long id);
	
	void delete(HocVien entity);

    KinhNghiemLamViec save(KinhNghiemLamViec knlv);

	List<KinhNghiemLamViec> findAllByMaLienKetAndLoaiLienKet(Long maLienKet, String loaiLienKet);

    void deleteKnlvById(Long id);

	KinhNghiemLamViec findOneKnlv(Long id);
}
