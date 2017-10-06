package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.ChuongTrinhDaoTao;
import vn.shp.portal.repository.ChuongTrinhDaoTaoRepository;
import vn.shp.portal.service.ChuongTrinhDaoTaoService;

import java.util.List;

@Service("ChuongTrinhDaoTaoService")
public class ChuongTrinhDaoTaoServiceImpl implements ChuongTrinhDaoTaoService {

	@Autowired
	private ChuongTrinhDaoTaoRepository chuongTrinhDaoTaoRepo;

	@Override
	public List<ChuongTrinhDaoTao> findAll() {
		List<ChuongTrinhDaoTao> chuongTrinhDaoTaoLst = chuongTrinhDaoTaoRepo.findAll();
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
	
}
