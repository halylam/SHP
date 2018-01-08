package vn.shp.app.service;

import vn.shp.app.entity.HocVien;
import vn.shp.app.entity.KinhNghiemLamViec;

import java.util.List;

public interface KinhNghiemLamViecService {

    KinhNghiemLamViec save(KinhNghiemLamViec knlv);

	List<KinhNghiemLamViec> findAllByMaLienKetAndLoaiLienKet(Long maLienKet, String loaiLienKet);

    void deleteKnlvById(Long id);

	KinhNghiemLamViec findOneKnlv(Long id);
}
