package vn.shp.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.LopHoc;
import vn.shp.app.constant.CoreConstant;
import vn.shp.app.repository.LopHocRepository;
import vn.shp.app.service.LopHocService;

@Service("LopHocService")
public class LopHocServiceImpl implements LopHocService {

	@Autowired
	private LopHocRepository lopHocRepo;

	@Override
	public List<LopHoc> findAll() {
		Pageable pageable = new PageRequest(0, CoreConstant.DATA_TABLE_LIMIT);
		List<LopHoc> lopHocLst = lopHocRepo.findAll(pageable).getContent();
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

	@Override
	public List<LopHoc> searchByFilters(String lopHocName, String lopHocCode) {
		return lopHocRepo.findBy(lopHocName, lopHocCode);
	}
	
}
