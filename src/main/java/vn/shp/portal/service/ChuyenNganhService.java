package vn.shp.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import vn.shp.app.entity.ChuyenNganh;
import vn.shp.portal.model.ChuyenNganhModel;

public interface ChuyenNganhService {

	List<ChuyenNganh> findAll();

	void save(ChuyenNganh chuyenNganh);
	
	ChuyenNganh findOne(Long id);

	void delete(Long id);
	
	void delete(ChuyenNganh entity);

	ModelAndView initSearch(ChuyenNganhModel condition, HttpServletRequest request);
	
	ChuyenNganh findByChuyenNganhCode(String chuyenNganhCode);
}
