package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.HocVienDk;
import vn.shp.portal.repository.HocVienDkRepository;
import vn.shp.portal.service.HocVienDkService;

import java.util.List;

@Service("HocVienDkService")
public class HocVienDkServiceImpl implements HocVienDkService {

	@Autowired
	private HocVienDkRepository bacDaoTaoRepo;

	@Override
	public List<HocVienDk> findAll() {
		List<HocVienDk> bacDaoTaoLst = bacDaoTaoRepo.findAll();
		return bacDaoTaoLst;
	}

	@Override
	@Transactional
	public void save(HocVienDk bacDaoTao) {
		bacDaoTaoRepo.save(bacDaoTao);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		bacDaoTaoRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(HocVienDk entity) {
		bacDaoTaoRepo.delete(entity);
	}

	@Override
	public HocVienDk findOne(Long id) {
		return bacDaoTaoRepo.findOne(id);
	}


}
