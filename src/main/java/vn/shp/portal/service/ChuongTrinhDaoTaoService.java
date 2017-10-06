package vn.shp.portal.service;

import vn.shp.app.entity.ChuongTrinhDaoTao;

import java.util.List;

public interface ChuongTrinhDaoTaoService {

	List<ChuongTrinhDaoTao> findAll();

	void save(ChuongTrinhDaoTao chuongTrinhDaoTao);
	
	ChuongTrinhDaoTao findOne(Long id);

	void delete(Long id);
	
	void delete(ChuongTrinhDaoTao entity);


	ChuongTrinhDaoTao findByChuongTrinhDaoTaoCode(String chuongTrinhDaoTaoCode);
}
