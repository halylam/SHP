package vn.shp.portal.service;

import vn.shp.app.entity.TonGiao;

import java.util.List;

public interface TonGiaoService {

	List<TonGiao> findAll();

	void save(TonGiao tonGiao);
	
	TonGiao findOne(Long id);

	void delete(Long id);
	
	void delete(TonGiao entity);

	TonGiao findByTonGiaoCode(String tonGiaoCode);
}
