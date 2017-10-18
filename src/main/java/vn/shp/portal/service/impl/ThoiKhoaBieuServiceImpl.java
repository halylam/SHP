package vn.shp.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.ThoiKhoaBieu;
import vn.shp.portal.repository.ThoiKhoaBieuRepository;
import vn.shp.portal.service.ThoiKhoaBieuService;

@Service("ThoiKhoaBieuService")
public class ThoiKhoaBieuServiceImpl implements ThoiKhoaBieuService {

	@Autowired
	private ThoiKhoaBieuRepository thoiKhoaBieuRepo;

	@Override
	public List<ThoiKhoaBieu> findAll() {
		List<ThoiKhoaBieu> thoiKhoaBieuLst = thoiKhoaBieuRepo.findAll();
		return thoiKhoaBieuLst;
	}

	@Override
	@Transactional
	public void save(ThoiKhoaBieu thoiKhoaBieu) {
		thoiKhoaBieuRepo.save(thoiKhoaBieu);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		thoiKhoaBieuRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(ThoiKhoaBieu entity) {
		thoiKhoaBieuRepo.delete(entity);
	}

	@Override
	public ThoiKhoaBieu findOne(Long id) {
		return thoiKhoaBieuRepo.findOne(id);
	}

}
