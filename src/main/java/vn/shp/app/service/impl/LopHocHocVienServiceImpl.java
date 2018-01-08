package vn.shp.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.LopHocHocVien;
import vn.shp.app.dao.LopHocHocVienDao;
import vn.shp.app.repository.LopHocHocVienRepository;
import vn.shp.app.service.LopHocHocVienService;

@Service("LopHocHocVienService")
@Transactional
public class LopHocHocVienServiceImpl implements LopHocHocVienService {

	@Autowired
	private LopHocHocVienRepository lopHocHocVienRepo;

	@Autowired
	LopHocHocVienDao lopHocHocVienDao;

	@Override
	public List<LopHocHocVien> findAll() {
		List<LopHocHocVien> lopHocHocVienLst = lopHocHocVienRepo.findAll();
		return lopHocHocVienLst;
	}

	@Override
	@Transactional
	public void save(LopHocHocVien lopHocHocVien) {
		lopHocHocVienRepo.save(lopHocHocVien);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		lopHocHocVienRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(LopHocHocVien entity) {
		lopHocHocVienRepo.delete(entity);
	}

	@Override
	public List<LopHocHocVien> findByLopHocId(Long lopHocId) {
		return lopHocHocVienDao.searchByLopHocId(lopHocId);
	}

	@Override
	public LopHocHocVien findOne(Long id) {
		return lopHocHocVienRepo.findOne(id);
	}
	
}
