package vn.shp.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import vn.shp.app.entity.ChuongTrinhDaoTao;
import vn.shp.portal.model.ChuongTrinhDaoTaoModel;

public interface ChuongTrinhDaoTaoService {

	List<ChuongTrinhDaoTao> findAll();

	void save(ChuongTrinhDaoTao chuongTrinhDaoTao);
	
	ChuongTrinhDaoTao findOne(Long id);

	void delete(Long id);
	
	void delete(ChuongTrinhDaoTao entity);

	ModelAndView initSearch(ChuongTrinhDaoTaoModel condition, HttpServletRequest request);
	
	ChuongTrinhDaoTao findByChuongTrinhDaoTaoCode(String chuongTrinhDaoTaoCode);
}
