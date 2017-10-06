package vn.shp.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.ChuyenNganh;
import vn.shp.portal.repository.ChuyenNganhRepository;
import vn.shp.portal.service.ChuyenNganhService;

@Service("ChuyenNganhService")
public class ChuyenNganhServiceImpl implements ChuyenNganhService {

	@Autowired
	private ChuyenNganhRepository chuyenNganhRepo;

	@Override
	public List<ChuyenNganh> findAll() {
		List<ChuyenNganh> chuyenNganhLst = chuyenNganhRepo.findAll();
		return chuyenNganhLst;
	}

	@Override
	@Transactional
	public void save(ChuyenNganh chuyenNganh) {
		chuyenNganhRepo.save(chuyenNganh);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		chuyenNganhRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(ChuyenNganh entity) {
		chuyenNganhRepo.delete(entity);
	}

	@Override
	public ChuyenNganh findOne(Long id) {
		return chuyenNganhRepo.findOne(id);
	}

	@Override
	public ChuyenNganh findByChuyenNganhCode(String chuyenNganhCode) {
		return chuyenNganhRepo.findByChuyenNganhCode(chuyenNganhCode);
	}
	
}
