package vn.shp.portal.service;

import vn.shp.app.entity.HocVi;

import java.util.List;

public interface HocViService {

	List<HocVi> findAll();

	void save(HocVi hocVi);
	
	HocVi findOne(Long id);

	void delete(Long id);
	
	void delete(HocVi entity);

	HocVi findByHocViCode(String hocViCode);
}
