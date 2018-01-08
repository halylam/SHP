package vn.shp.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.TonGiao;
import vn.shp.app.repository.TonGiaoRepository;
import vn.shp.app.service.TonGiaoService;

import java.util.List;

@Service("TonGiaoService")
public class TonGiaoServiceImpl implements TonGiaoService {

	@Autowired
	private TonGiaoRepository tonGiaoRepo;

	@Override
	public List<TonGiao> findAll() {
		List<TonGiao> tonGiaoLst = tonGiaoRepo.findAll();
		return tonGiaoLst;
	}

	@Override
	@Transactional
	public void save(TonGiao tonGiao) {
		tonGiaoRepo.save(tonGiao);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		tonGiaoRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(TonGiao entity) {
		tonGiaoRepo.delete(entity);
	}

	@Override
	public TonGiao findOne(Long id) {
		return tonGiaoRepo.findOne(id);
	}

	@Override
	public TonGiao findByTonGiaoCode(String tonGiaoCode) {
		return tonGiaoRepo.findByTonGiaoCode(tonGiaoCode);
	}
	
}
