package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.GiangVien;
import vn.shp.portal.repository.GiangVienRepository;
import vn.shp.portal.repository.KinhNghiemLamViecRepository;
import vn.shp.portal.service.GiangVienService;

import java.util.List;

@Service("giangVienService")
public class GiangVienServiceImpl implements GiangVienService {

	@Autowired
	private GiangVienRepository giangVienRepository;

	@Autowired
	private KinhNghiemLamViecRepository kinhNghiemLamViecRepository;

	@Override
	public List<GiangVien> findAll() {
		List<GiangVien> entityLst = giangVienRepository.findAll();
		return entityLst;
	}

	@Override
	@Transactional
	public void save(GiangVien entity) {
		giangVienRepository.save(entity);
		if(entity.getId() != null){
			String maHV = "GV" + String.format("%05d", entity.getId());
			entity.setMaGiangVien(maHV);
			giangVienRepository.save(entity);
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		giangVienRepository.delete(id);
	}

	@Override
	@Transactional
	public void delete(GiangVien entity) {
		giangVienRepository.delete(entity);
	}

	@Override
	public GiangVien findOne(Long id) {
		return giangVienRepository.findOne(id);
	}


}
