package vn.shp.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.LopHoc;
import vn.shp.portal.repository.LopHocRepository;
import vn.shp.portal.service.LopHocService;

@Service("LopHocService")
public class LopHocServiceImpl implements LopHocService {

	@Autowired
	private LopHocRepository lopHocRepo;

	@Override
	public List<LopHoc> findAll() {
		List<LopHoc> lopHocLst = lopHocRepo.findAll();
		return lopHocLst;
	}

	@Override
	@Transactional
	public void save(LopHoc lopHoc) {
		lopHocRepo.save(lopHoc);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		lopHocRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(LopHoc entity) {
		lopHocRepo.delete(entity);
	}

	@Override
	public LopHoc findOne(Long id) {
		return lopHocRepo.findOne(id);
	}

	@Override
	public LopHoc findByLopHocCode(String lopHocCode) {
		return lopHocRepo.findByLopHocCode(lopHocCode);
	}
	
}
