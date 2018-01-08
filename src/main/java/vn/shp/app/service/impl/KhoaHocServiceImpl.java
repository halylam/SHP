package vn.shp.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.KhoaHoc;
import vn.shp.app.constant.CoreConstant;
import vn.shp.app.dao.KhoaHocDao;
import vn.shp.app.repository.KhoaHocRepository;
import vn.shp.app.service.KhoaHocService;

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
		Pageable pageable = new PageRequest(0, CoreConstant.DATA_TABLE_LIMIT);
		List<KhoaHoc> khoaHocLst = khoaHocRepo.findAll(pageable).getContent();
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

	@Override
	public List<KhoaHoc> searchByFilters(String khoaHocName, String khoaHocCode) {
		return khoaHocRepo.findBy(khoaHocName, khoaHocCode);
	}
	
}
