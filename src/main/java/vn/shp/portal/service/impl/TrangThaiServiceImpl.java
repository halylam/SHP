package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.TrangThai;
import vn.shp.portal.repository.TrangThaiRepository;
import vn.shp.portal.service.TrangThaiService;

import java.util.List;

@Service("TrangThaiService")
public class TrangThaiServiceImpl implements TrangThaiService {

	@Autowired
	private TrangThaiRepository trangThaiRepo;

	@Override
	public List<TrangThai> findAll() {
		List<TrangThai> trangThaiLst = trangThaiRepo.findAll();
		return trangThaiLst;
	}

	@Override
	@Transactional
	public void save(TrangThai trangThai) {
		trangThaiRepo.save(trangThai);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		trangThaiRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(TrangThai entity) {
		trangThaiRepo.delete(entity);
	}

	@Override
	public TrangThai findOne(Long id) {
		return trangThaiRepo.findOne(id);
	}

	@Override
	public TrangThai findByTrangThaiCode(String trangThaiCode) {
		return trangThaiRepo.findByTrangThaiCode(trangThaiCode);
	}
	
}
