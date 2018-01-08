package vn.shp.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.CaHoc;
import vn.shp.app.constant.CoreConstant;
import vn.shp.app.repository.CaHocRepository;
import vn.shp.app.service.CaHocService;

import java.util.List;

@Service("CaHocService")
public class CaHocServiceImpl implements CaHocService {

	@Autowired
	private CaHocRepository caHocRepo;

	@Override
	public List<CaHoc> findAll() {
		Pageable pageable = new PageRequest(0, CoreConstant.DATA_TABLE_LIMIT);
		List<CaHoc> caHocLst = caHocRepo.findAll(pageable).getContent();
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

	@Override
	public List<CaHoc> searchByFilters(String caHocName, String caHocCode) {
		return caHocRepo.findBy(caHocName, caHocCode);
	}
	
}
