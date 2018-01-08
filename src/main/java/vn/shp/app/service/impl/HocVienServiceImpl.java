package vn.shp.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.HocVien;
import vn.shp.app.constant.CoreConstant;
import vn.shp.app.repository.HocVienRepository;
import vn.shp.app.repository.KinhNghiemLamViecRepository;
import vn.shp.app.service.HocVienService;

import java.util.List;

@Service("hocVienService")
public class HocVienServiceImpl implements HocVienService {

	@Autowired
	private HocVienRepository hocVienRepository;

	@Autowired
	private KinhNghiemLamViecRepository kinhNghiemLamViecRepository;

	@Override
	public List<HocVien> findAll() {
		Pageable pageable = new PageRequest(0, CoreConstant.DATA_TABLE_LIMIT);
		List<HocVien> entityLst = hocVienRepository.findAll(pageable).getContent();
		return entityLst;
	}

	@Override
	@Transactional
	public void save(HocVien entity) {
		hocVienRepository.save(entity);
		if(entity.getId() != null && !entity.getMaHocVien().startsWith("HV")){
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
	public HocVien findByMaHocVien(String maHocVien){
		return hocVienRepository.findByMaHocVien(maHocVien);
	}

}
