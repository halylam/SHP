package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.KhoaHoc;
import vn.shp.portal.dao.KhoaHocDao;
import vn.shp.portal.repository.KhoaHocRepository;
import vn.shp.portal.service.KhoaHocService;

import java.util.List;

@Service("khoaHocService")
@Transactional
public class KhoaHocServiceImpl implements KhoaHocService {

	@Autowired
	private KhoaHocRepository khoaHocRepo;

	@Autowired
	private KhoaHocDao khoaHocDao;

	@Override
	public List<KhoaHoc> findAll() {
		List<KhoaHoc> khoaHocLst = khoaHocRepo.findAll();
		return khoaHocLst;
	}

	@Override
	@Transactional
	public void save(KhoaHoc khoaHoc) {
		khoaHocRepo.save(khoaHoc);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		khoaHocRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(KhoaHoc entity) {
		khoaHocRepo.delete(entity);
	}

	@Override
	public KhoaHoc findOne(Long id) {
		return khoaHocRepo.findOne(id);
	}

	@Override
	public KhoaHoc findByKhoaHocCode(String khoaHocCode) {
		return khoaHocRepo.findByKhoaHocCode(khoaHocCode);
	}

	@Override
	public List<KhoaHoc>  findKhoaHocDangKy(){
		return khoaHocDao.findKhoaHocDangKy();
	}
	
}
