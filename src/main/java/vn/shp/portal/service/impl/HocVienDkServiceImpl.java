package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import vn.shp.app.entity.HocVien;
import vn.shp.app.entity.HocVienDk;
import vn.shp.portal.dao.HocVienDkDao;
import vn.shp.portal.filter.HocVienDkFilter;
import vn.shp.portal.repository.HocVienDkRepository;
import vn.shp.portal.service.HocVienDkService;

import java.util.ArrayList;
import java.util.List;

@Service("hocVienDkService")
public class HocVienDkServiceImpl implements HocVienDkService {

	@Autowired
	private HocVienDkRepository hocVienDkRepository;

	@Autowired
	private HocVienDkDao hocVienDkDao;

	@Override
	public List<HocVienDk> findAll() {
		List<HocVienDk> bacDaoTaoLst = hocVienDkRepository.findAll();
		return bacDaoTaoLst;
	}

	@Override
	@Transactional
	public void save(HocVienDk bacDaoTao) {
		hocVienDkRepository.save(bacDaoTao);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		hocVienDkRepository.delete(id);
	}

	@Override
	@Transactional
	public void delete(HocVienDk entity) {
		hocVienDkRepository.delete(entity);
	}

	@Override
	public HocVienDk findOne(Long id) {
		return hocVienDkRepository.findOne(id);
	}

	@Override
	public List<HocVienDk> findByMaHocVien(String maHocVien){
		return hocVienDkRepository.findByMaHocVien(maHocVien);
	}

	@Override
	public List<HocVien> findByMaKhoaHoc(String maKhoaHoc){
		List<HocVienDk> lstHvdk = hocVienDkRepository.findByMaKhoaHoc(maKhoaHoc);
		if(!CollectionUtils.isEmpty(lstHvdk)){
			List<HocVien> lstHocVien = new ArrayList<>();
			for (HocVienDk hocVienDk : lstHvdk) {
				lstHocVien.add(hocVienDk.getHocVien());
			}
			return  lstHocVien;
		}
		return null;
	}

	@Override
	public List<HocVien> findByMaKhoaHoc(Long khoaHocId){
		List<HocVienDk> lstHvdk = hocVienDkRepository.findByKhoaHocId(khoaHocId);
		if(!CollectionUtils.isEmpty(lstHvdk)){
			List<HocVien> lstHocVien = new ArrayList<>();
			for (HocVienDk hocVienDk : lstHvdk) {
				lstHocVien.add(hocVienDk.getHocVien());
			}
			return  lstHocVien;
		}
		return null;
	}

	@Override
	public List<HocVienDk> searchByFilters(HocVienDkFilter filter) {
		return hocVienDkDao.searchByFilters(filter);
	}

}
