package vn.shp.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.ChuyenMon;
import vn.shp.app.repository.ChuyenMonRepository;
import vn.shp.app.service.ChuyenMonService;

import java.util.List;

@Service("chuyenMonService")
public class ChuyenMonServiceImpl implements ChuyenMonService {

	@Autowired
	private ChuyenMonRepository chuyenMonRepo;

	@Override
	public List<ChuyenMon> findAll() {
		List<ChuyenMon> chuyenMonLst = chuyenMonRepo.findAll();
		return chuyenMonLst;
	}

	@Override
	@Transactional
	public void save(ChuyenMon chuyenMon) {
		chuyenMonRepo.save(chuyenMon);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		chuyenMonRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(ChuyenMon entity) {
		chuyenMonRepo.delete(entity);
	}

	@Override
	public ChuyenMon findOne(Long id) {
		return chuyenMonRepo.findOne(id);
	}

	@Override
	public ChuyenMon findByChuyenMonCode(String chuyenMonCode) {
		return chuyenMonRepo.findByChuyenMonCode(chuyenMonCode);
	}
	
}
