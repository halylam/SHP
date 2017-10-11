package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.HocVien;
import vn.shp.app.entity.KinhNghiemLamViec;
import vn.shp.portal.repository.HocVienRepository;
import vn.shp.portal.repository.KinhNghiemLamViecRepository;
import vn.shp.portal.service.HocVienService;

import java.util.List;

@Service("hocVienService")
public class HocVienServiceImpl implements HocVienService {

	@Autowired
	private HocVienRepository hocVienRepository;

	@Autowired
	private KinhNghiemLamViecRepository kinhNghiemLamViecRepository;

	@Override
	public List<HocVien> findAll() {
		List<HocVien> entityLst = hocVienRepository.findAll();
		return entityLst;
	}

	@Override
	@Transactional
	public void save(HocVien entity) {
		hocVienRepository.save(entity);
		if(entity.getId() != null){
			String maHV = "HV" + String.format("%05d", entity.getId());
			entity.setMaHocVien(maHV);
			hocVienRepository.save(entity);
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		hocVienRepository.delete(id);
	}

	@Override
	@Transactional
	public void delete(HocVien entity) {
		hocVienRepository.delete(entity);
	}

	@Override
	public HocVien findOne(Long id) {
		return hocVienRepository.findOne(id);
	}

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
