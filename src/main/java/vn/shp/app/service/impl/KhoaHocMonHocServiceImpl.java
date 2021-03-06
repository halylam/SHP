package vn.shp.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.KhoaHocMonHoc;
import vn.shp.app.dao.KhoaHocMonHocDao;
import vn.shp.app.repository.KhoaHocMonHocRepository;
import vn.shp.app.service.KhoaHocMonHocService;

import java.util.List;

@Service("KhoaHocMonHocService")
@Transactional
public class KhoaHocMonHocServiceImpl implements KhoaHocMonHocService {

	@Autowired
	private KhoaHocMonHocRepository khoaHocMonHocRepo;

	@Autowired
	KhoaHocMonHocDao khoaHocMonHocDao;

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
		return khoaHocMonHocDao.searchByKhoaHocId(khoaHocId);
	}

	@Override
	public KhoaHocMonHoc findOne(Long id) {
		return khoaHocMonHocRepo.findOne(id);
	}
	
}
