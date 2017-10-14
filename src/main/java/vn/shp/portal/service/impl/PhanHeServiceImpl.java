package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.PhanHe;
import vn.shp.portal.repository.PhanHeRepository;
import vn.shp.portal.service.PhanHeService;

import java.util.List;

@Service("PhanHeService")
public class PhanHeServiceImpl implements PhanHeService {

	@Autowired
	private PhanHeRepository phanHeRepo;

	@Override
	public List<PhanHe> findAll() {
		List<PhanHe> phanHeLst = phanHeRepo.findAll();
		return phanHeLst;
	}

	@Override
	@Transactional
	public void save(PhanHe phanHe) {
		phanHeRepo.save(phanHe);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		phanHeRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(PhanHe entity) {
		phanHeRepo.delete(entity);
	}

	@Override
	public PhanHe findOne(Long id) {
		return phanHeRepo.findOne(id);
	}

	@Override
	public PhanHe findByPhanHeCode(String phanHeCode) {
		return phanHeRepo.findByPhanHeCode(phanHeCode);
	}
	
}
