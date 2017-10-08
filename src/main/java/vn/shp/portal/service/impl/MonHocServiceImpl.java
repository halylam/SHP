package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.MonHoc;
import vn.shp.portal.repository.MonHocRepository;
import vn.shp.portal.service.MonHocService;

import java.util.List;

@Service("MonHocService")
public class MonHocServiceImpl implements MonHocService {

	@Autowired
	private MonHocRepository monHocRepo;

	@Override
	public List<MonHoc> findAll() {
		List<MonHoc> monHocLst = monHocRepo.findAll();
		return monHocLst;
	}

	@Override
	@Transactional
	public void save(MonHoc monHoc) {
		monHocRepo.save(monHoc);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		monHocRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(MonHoc entity) {
		monHocRepo.delete(entity);
	}

	@Override
	public MonHoc findOne(Long id) {
		return monHocRepo.findOne(id);
	}

	@Override
	public MonHoc findByMonHocCode(String monHocCode) {
		return monHocRepo.findByMonHocCode(monHocCode);
	}
	
}