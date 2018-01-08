package vn.shp.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.ChuongTrinhDaoTao;
import vn.shp.app.constant.CoreConstant;
import vn.shp.app.repository.ChuongTrinhDaoTaoRepository;
import vn.shp.app.service.ChuongTrinhDaoTaoService;

import java.util.List;

@Service("ChuongTrinhDaoTaoService")
public class ChuongTrinhDaoTaoServiceImpl implements ChuongTrinhDaoTaoService {

	@Autowired
	private ChuongTrinhDaoTaoRepository chuongTrinhDaoTaoRepo;

	@Override
	public List<ChuongTrinhDaoTao> findAll() {
		Pageable pageable = new PageRequest(0, CoreConstant.DATA_TABLE_LIMIT);
		List<ChuongTrinhDaoTao> chuongTrinhDaoTaoLst = chuongTrinhDaoTaoRepo.findAll(pageable).getContent();
		return chuongTrinhDaoTaoLst;
	}

	@Override
	@Transactional
	public void save(ChuongTrinhDaoTao chuongTrinhDaoTao) {
		chuongTrinhDaoTaoRepo.save(chuongTrinhDaoTao);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		chuongTrinhDaoTaoRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(ChuongTrinhDaoTao entity) {
		chuongTrinhDaoTaoRepo.delete(entity);
	}

	@Override
	public ChuongTrinhDaoTao findOne(Long id) {
		return chuongTrinhDaoTaoRepo.findOne(id);
	}


	@Override
	public ChuongTrinhDaoTao findByChuongTrinhDaoTaoCode(String chuongTrinhDaoTaoCode) {
		return chuongTrinhDaoTaoRepo.findByChuongTrinhDaoTaoCode(chuongTrinhDaoTaoCode);
	}

	@Override
	public List<ChuongTrinhDaoTao> searchByFilters(String chuongTrinhDaoTaoName, String chuongTrinhDaoTaoCode) {
		return chuongTrinhDaoTaoRepo.findBy(chuongTrinhDaoTaoName, chuongTrinhDaoTaoCode);
	}
	
}
