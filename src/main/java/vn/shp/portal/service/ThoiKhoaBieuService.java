package vn.shp.portal.service;

import java.util.List;

import vn.shp.app.entity.ThoiKhoaBieu;

public interface ThoiKhoaBieuService {

	List<ThoiKhoaBieu> findAll();

	void save(ThoiKhoaBieu thoiKhoaBieu);
	
	ThoiKhoaBieu findOne(Long id);

	void delete(Long id);
	
	void delete(ThoiKhoaBieu entity);
}
