package vn.shp.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.ChuyenNganh;
import vn.shp.app.constant.CoreConstant;
import vn.shp.app.repository.ChuyenNganhRepository;
import vn.shp.app.service.ChuyenNganhService;

@Service("ChuyenNganhService")
public class ChuyenNganhServiceImpl implements ChuyenNganhService {

	@Autowired
	private ChuyenNganhRepository chuyenNganhRepo;

	@Override
	public List<ChuyenNganh> findAll() {
		Pageable pageable = new PageRequest(0, CoreConstant.DATA_TABLE_LIMIT);
		List<ChuyenNganh> chuyenNganhLst = chuyenNganhRepo.findAll(pageable).getContent();
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

	@Override
	public List<ChuyenNganh> searchByFilters(String chuyenNganhName, String chuyenNganhCode) {
		return chuyenNganhRepo.findBy(chuyenNganhName, chuyenNganhCode);
	}
	
}
