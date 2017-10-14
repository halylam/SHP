package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.HocVi;
import vn.shp.portal.repository.HocViRepository;
import vn.shp.portal.service.HocViService;

import java.util.List;

@Service("HocViService")
public class HocViServiceImpl implements HocViService {

	@Autowired
	private HocViRepository hocViRepo;

	@Override
	public List<HocVi> findAll() {
		List<HocVi> hocViLst = hocViRepo.findAll();
		return hocViLst;
	}

	@Override
	@Transactional
	public void save(HocVi hocVi) {
		hocViRepo.save(hocVi);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		hocViRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(HocVi entity) {
		hocViRepo.delete(entity);
	}

	@Override
	public HocVi findOne(Long id) {
		return hocViRepo.findOne(id);
	}

	@Override
	public HocVi findByHocViCode(String hocViCode) {
		return hocViRepo.findByHocViCode(hocViCode);
	}
	
}
