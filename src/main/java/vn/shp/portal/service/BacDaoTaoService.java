package vn.shp.portal.service;

import vn.shp.app.entity.BacDaoTao;

import java.util.List;

public interface BacDaoTaoService {

	List<BacDaoTao> findAll();

	void save(BacDaoTao bacDaoTao);
	
	BacDaoTao findOne(Long id);

	void delete(Long id);
	
	void delete(BacDaoTao entity);

	BacDaoTao findByBacDaoTaoCode(String bacDaoTaoCode);
}
