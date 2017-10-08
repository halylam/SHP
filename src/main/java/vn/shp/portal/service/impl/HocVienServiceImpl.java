package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.HocVien;
import vn.shp.portal.repository.HocVienRepository;
import vn.shp.portal.service.HocVienService;

import java.util.List;

@Service("hocVienService")
public class HocVienServiceImpl implements HocVienService {

	@Autowired
	private HocVienRepository hocVienRepository;

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
}
