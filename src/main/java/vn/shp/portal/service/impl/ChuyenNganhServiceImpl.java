package vn.shp.portal.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import vn.shp.app.entity.ChuyenNganh;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.PageWrapper;
import vn.shp.portal.model.ChuyenNganhModel;
import vn.shp.portal.repository.ChuyenNganhRepository;
import vn.shp.portal.service.ChuyenNganhService;

@Service("ChuyenNganhService")
public class ChuyenNganhServiceImpl implements ChuyenNganhService {

	@Autowired
	private ChuyenNganhRepository chuyenNganhRepo;

	@Override
	public List<ChuyenNganh> findAll() {
		List<ChuyenNganh> chuyenNganhLst = chuyenNganhRepo.findAll();
		return chuyenNganhLst;
	}

	@Override
	@Transactional
	public void save(ChuyenNganh chuyenNganh) {
		chuyenNganhRepo.save(chuyenNganh);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		chuyenNganhRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(ChuyenNganh entity) {
		chuyenNganhRepo.delete(entity);
	}

	@Override
	public ChuyenNganh findOne(Long id) {
		return chuyenNganhRepo.findOne(id);
	}

	@Override
	public ModelAndView initSearch(ChuyenNganhModel model, HttpServletRequest request) {
		
		String pageParam = request.getParameter(CoreConstant.CONST_PAGE);
		int page = 0;
		if(!StringUtils.isEmpty(pageParam) && !pageParam.equals("0")) {
			page = Integer.parseInt(pageParam) - 1;
		}
		int sizeOfPage = CoreConstant.DATA_TABLE_LIMIT;
		Pageable pageable = new PageRequest(page, sizeOfPage);
		
		if (model == null) {
			model = new ChuyenNganhModel();
		}
		
		ChuyenNganh entity = model.getEntity();
		Page<ChuyenNganh> result;
		int count;
		if (entity != null && (!entity.getChuyenNganhName().isEmpty() || !entity.getChuyenNganhCode().isEmpty())) {
			result = chuyenNganhRepo.findBy(entity.getChuyenNganhName(), entity.getChuyenNganhCode(), pageable);
			count = chuyenNganhRepo.findBy(entity.getChuyenNganhName(), entity.getChuyenNganhCode()).size();
		} else {
			result = chuyenNganhRepo.findAll(pageable);
			count = chuyenNganhRepo.findAll().size();
		}
		
		ModelAndView mav = new ModelAndView();
		PageWrapper<ChuyenNganh> pageWrapper = new PageWrapper<ChuyenNganh>(page + 1, sizeOfPage);
		
		pageWrapper.setDataAndCount(result.getContent(), count);
		mav.addObject("pageWrapper", pageWrapper);
		return mav;
		
	}

	@Override
	public ChuyenNganh findByChuyenNganhCode(String chuyenNganhCode) {
		return chuyenNganhRepo.findByChuyenNganhCode(chuyenNganhCode);
	}
	
}
