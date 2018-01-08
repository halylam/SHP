package vn.shp.app.service;

import vn.shp.app.entity.DanToc;

import java.util.List;

public interface DanTocService {

	List<DanToc> findAll();

	void save(DanToc danToc);
	
	DanToc findOne(Long id);

	void delete(Long id);
	
	void delete(DanToc entity);

	DanToc findByDanTocCode(String danTocCode);
}
