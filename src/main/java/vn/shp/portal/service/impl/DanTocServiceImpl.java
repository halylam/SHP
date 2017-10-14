package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.DanToc;
import vn.shp.portal.repository.DanTocRepository;
import vn.shp.portal.service.DanTocService;

import java.util.List;

@Service("DanTocService")
public class DanTocServiceImpl implements DanTocService {

	@Autowired
	private DanTocRepository danTocRepo;

	@Override
	public List<DanToc> findAll() {
		List<DanToc> danTocLst = danTocRepo.findAll();
		return danTocLst;
	}

	@Override
	@Transactional
	public void save(DanToc danToc) {
		danTocRepo.save(danToc);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		danTocRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(DanToc entity) {
		danTocRepo.delete(entity);
	}

	@Override
	public DanToc findOne(Long id) {
		return danTocRepo.findOne(id);
	}

	@Override
	public DanToc findByDanTocCode(String danTocCode) {
		return danTocRepo.findByDanTocCode(danTocCode);
	}
	
}
