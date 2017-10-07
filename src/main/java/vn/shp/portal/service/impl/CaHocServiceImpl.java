package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.CaHoc;
import vn.shp.portal.repository.CaHocRepository;
import vn.shp.portal.service.CaHocService;

import java.util.List;

@Service("CaHocService")
public class CaHocServiceImpl implements CaHocService {

	@Autowired
	private CaHocRepository caHocRepo;

	@Override
	public List<CaHoc> findAll() {
		List<CaHoc> caHocLst = caHocRepo.findAll();
		return caHocLst;
	}

	@Override
	@Transactional
	public void save(CaHoc caHoc) {
		caHocRepo.save(caHoc);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		caHocRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(CaHoc entity) {
		caHocRepo.delete(entity);
	}

	@Override
	public CaHoc findOne(Long id) {
		return caHocRepo.findOne(id);
	}

	@Override
	public CaHoc findByCaHocCode(String caHocCode) {
		return caHocRepo.findByCaHocCode(caHocCode);
	}
	
}
