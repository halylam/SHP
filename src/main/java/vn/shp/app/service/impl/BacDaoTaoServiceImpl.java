package vn.shp.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.BacDaoTao;
import vn.shp.app.constant.CoreConstant;
import vn.shp.app.repository.BacDaoTaoRepository;
import vn.shp.app.service.BacDaoTaoService;

@Service("BacDaoTaoService")
public class BacDaoTaoServiceImpl implements BacDaoTaoService {

	@Autowired
	private BacDaoTaoRepository bacDaoTaoRepo;

	@Override
	public List<BacDaoTao> findAll() {
		Pageable pageable = new PageRequest(0, CoreConstant.DATA_TABLE_LIMIT);
		List<BacDaoTao> bacDaoTaoLst = bacDaoTaoRepo.findAll(pageable).getContent();
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

	@Override
	public List<BacDaoTao> searchByFilters(String bacDaoTaoName, String bacDaoTaoCode) {
		return bacDaoTaoRepo.findBy(bacDaoTaoName, bacDaoTaoCode);
	}
	
}
