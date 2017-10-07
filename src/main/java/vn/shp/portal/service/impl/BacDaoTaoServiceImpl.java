package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.BacDaoTao;
import vn.shp.portal.repository.BacDaoTaoRepository;
import vn.shp.portal.service.BacDaoTaoService;

import java.util.List;

@Service("BacDaoTaoService")
public class BacDaoTaoServiceImpl implements BacDaoTaoService {

	@Autowired
	private BacDaoTaoRepository bacDaoTaoRepo;

	@Override
	public List<BacDaoTao> findAll() {
		List<BacDaoTao> bacDaoTaoLst = bacDaoTaoRepo.findAll();
		return bacDaoTaoLst;
	}

	@Override
	@Transactional
	public void save(BacDaoTao bacDaoTao) {
		bacDaoTaoRepo.save(bacDaoTao);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		bacDaoTaoRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(BacDaoTao entity) {
		bacDaoTaoRepo.delete(entity);
	}

	@Override
	public BacDaoTao findOne(Long id) {
		return bacDaoTaoRepo.findOne(id);
	}

	@Override
	public BacDaoTao findByBacDaoTaoCode(String bacDaoTaoCode) {
		return bacDaoTaoRepo.findByBacDaoTaoCode(bacDaoTaoCode);
	}
	
}
