package vn.shp.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.shp.app.entity.KinhNghiemLamViec;
import vn.shp.app.repository.KinhNghiemLamViecRepository;
import vn.shp.app.service.KinhNghiemLamViecService;

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
