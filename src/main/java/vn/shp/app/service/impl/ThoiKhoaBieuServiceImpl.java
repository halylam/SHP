package vn.shp.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.ThoiKhoaBieu;
import vn.shp.app.constant.CoreConstant;
import vn.shp.app.repository.ThoiKhoaBieuRepository;
import vn.shp.app.service.ThoiKhoaBieuService;

@Service("ThoiKhoaBieuService")
public class ThoiKhoaBieuServiceImpl implements ThoiKhoaBieuService {

	@Autowired
	private ThoiKhoaBieuRepository thoiKhoaBieuRepo;

	@Override
	public List<ThoiKhoaBieu> findAll() {
		Pageable pageable = new PageRequest(0, CoreConstant.DATA_TABLE_LIMIT);
		List<ThoiKhoaBieu> thoiKhoaBieuLst = thoiKhoaBieuRepo.findAll(pageable).getContent();
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
