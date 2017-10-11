package vn.shp.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.LoaiLopHoc;
import vn.shp.portal.repository.LoaiLopHocRepository;
import vn.shp.portal.service.LoaiLopHocService;

@Service("LoaiLopHocService")
public class LoaiLopHocServiceImpl implements LoaiLopHocService {

	@Autowired
	private LoaiLopHocRepository loaiLopHocRepo;

	@Override
	public List<LoaiLopHoc> findAll() {
		List<LoaiLopHoc> loaiLopHocLst = loaiLopHocRepo.findAll();
		return loaiLopHocLst;
	}

	@Override
	@Transactional
	public void save(LoaiLopHoc loaiLopHoc) {
		loaiLopHocRepo.save(loaiLopHoc);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		loaiLopHocRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(LoaiLopHoc entity) {
		loaiLopHocRepo.delete(entity);
	}

	@Override
	public LoaiLopHoc findOne(Long id) {
		return loaiLopHocRepo.findOne(id);
	}

	@Override
	public LoaiLopHoc findByLoaiLopHocCode(String loaiLopHocCode) {
		return loaiLopHocRepo.findByLoaiLopHocCode(loaiLopHocCode);
	}
	
}
