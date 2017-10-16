package vn.shp.portal.service;

import vn.shp.app.entity.HocVienDk;

import java.util.List;

public interface HocVienDkService {

	List<HocVienDk> findAll();

	void save(HocVienDk bacDaoTao);

	HocVienDk findOne(Long id);

	void delete(Long id);

	void delete(HocVienDk entity);

}
