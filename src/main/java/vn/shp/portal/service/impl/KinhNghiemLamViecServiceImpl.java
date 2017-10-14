package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.HocVien;
import vn.shp.app.entity.KinhNghiemLamViec;
import vn.shp.portal.repository.HocVienRepository;
import vn.shp.portal.repository.KinhNghiemLamViecRepository;
import vn.shp.portal.service.HocVienService;
import vn.shp.portal.service.KinhNghiemLamViecService;

import java.util.List;

@Service("kinhNghiemLamViecService")
public class KinhNghiemLamViecServiceImpl implements KinhNghiemLamViecService {

	@Autowired
	private KinhNghiemLamViecRepository kinhNghiemLamViecRepository;


	@Override
	public KinhNghiemLamViec save(KinhNghiemLamViec knlv){
		return kinhNghiemLamViecRepository.save(knlv);
	}

	@Override
	public List<KinhNghiemLamViec> findAllByMaLienKetAndLoaiLienKet(Long maLienKet, String loaiLienKet){
		return kinhNghiemLamViecRepository.findAllByMaLienKetAndLoaiLienKet(maLienKet,loaiLienKet);
	}

	@Override
	public void deleteKnlvById(Long id){
		kinhNghiemLamViecRepository.delete(id);
	}

	@Override
	public KinhNghiemLamViec findOneKnlv(Long id){
		return kinhNghiemLamViecRepository.findOne(id);
	}
}
