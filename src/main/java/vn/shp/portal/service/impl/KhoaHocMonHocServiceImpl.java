package vn.shp.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.KhoaHocMonHoc;
import vn.shp.portal.repository.KhoaHocMonHocRepository;
import vn.shp.portal.service.KhoaHocMonHocService;

@Service("KhoaHocMonHocService")
public class KhoaHocMonHocServiceImpl implements KhoaHocMonHocService {

	@Autowired
	private KhoaHocMonHocRepository khoaHocMonHocRepo;

	@Override
	public List<KhoaHocMonHoc> findAll() {
		List<KhoaHocMonHoc> khoaHocMonHocLst = khoaHocMonHocRepo.findAll();
		return khoaHocMonHocLst;
	}

	@Override
	@Transactional
	public void save(KhoaHocMonHoc khoaHocMonHoc) {
		khoaHocMonHocRepo.save(khoaHocMonHoc);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		khoaHocMonHocRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(KhoaHocMonHoc entity) {
		khoaHocMonHocRepo.delete(entity);
	}

	@Override
	public List<KhoaHocMonHoc> findByKhoaHocId(Long khoaHocId) {
		return khoaHocMonHocRepo.findBy(khoaHocId);
	}

	@Override
	public KhoaHocMonHoc findOne(Long id) {
		return khoaHocMonHocRepo.findOne(id);
	}
	
}
