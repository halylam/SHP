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
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.PageWrapper;
import vn.shp.app.entity.ChuongTrinhDaoTao;
import vn.shp.portal.model.ChuongTrinhDaoTaoModel;
import vn.shp.portal.repository.ChuongTrinhDaoTaoRepository;
import vn.shp.portal.service.ChuongTrinhDaoTaoService;

@Service("ChuongTrinhDaoTaoService")
public class ChuongTrinhDaoTaoServiceImpl implements ChuongTrinhDaoTaoService {

	@Autowired
	private ChuongTrinhDaoTaoRepository chuongTrinhDaoTaoRepo;

	@Override
	public List<ChuongTrinhDaoTao> findAll() {
		List<ChuongTrinhDaoTao> chuongTrinhDaoTaoLst = chuongTrinhDaoTaoRepo.findAll();
		return chuongTrinhDaoTaoLst;
	}

	@Override
	@Transactional
	public void save(ChuongTrinhDaoTao chuongTrinhDaoTao) {
		chuongTrinhDaoTaoRepo.save(chuongTrinhDaoTao);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		chuongTrinhDaoTaoRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(ChuongTrinhDaoTao entity) {
		chuongTrinhDaoTaoRepo.delete(entity);
	}

	@Override
	public ChuongTrinhDaoTao findOne(Long id) {
		return chuongTrinhDaoTaoRepo.findOne(id);
	}

	@Override
	public ModelAndView initSearch(ChuongTrinhDaoTaoModel model, HttpServletRequest request) {
		
		String pageParam = request.getParameter(CoreConstant.CONST_PAGE);
		int page = 0;
		if(!StringUtils.isEmpty(pageParam) && !pageParam.equals("0")) {
			page = Integer.parseInt(pageParam) - 1;
		}
		int sizeOfPage = CoreConstant.DATA_TABLE_LIMIT;
		Pageable pageable = new PageRequest(page, sizeOfPage);
		
		if (model == null) {
			model = new ChuongTrinhDaoTaoModel();
		}
		
		ChuongTrinhDaoTao entity = model.getEntity();
		Page<ChuongTrinhDaoTao> result;
		int count;
		if (entity != null && (!entity.getChuongTrinhDaoTaoName().isEmpty() || !entity.getChuongTrinhDaoTaoCode().isEmpty())) {
			result = chuongTrinhDaoTaoRepo.findBy(entity.getChuongTrinhDaoTaoName(), entity.getChuongTrinhDaoTaoCode(), pageable);
			count = chuongTrinhDaoTaoRepo.findBy(entity.getChuongTrinhDaoTaoName(), entity.getChuongTrinhDaoTaoCode()).size();
		} else {
			result = chuongTrinhDaoTaoRepo.findAll(pageable);
			count = chuongTrinhDaoTaoRepo.findAll().size();
		}
		
		ModelAndView mav = new ModelAndView();
		PageWrapper<ChuongTrinhDaoTao> pageWrapper = new PageWrapper<ChuongTrinhDaoTao>(page + 1, sizeOfPage);
		
		pageWrapper.setDataAndCount(result.getContent(), count);
		mav.addObject("pageWrapper", pageWrapper);
		return mav;
		
	}

	@Override
	public ChuongTrinhDaoTao findByChuongTrinhDaoTaoCode(String chuongTrinhDaoTaoCode) {
		return chuongTrinhDaoTaoRepo.findByChuongTrinhDaoTaoCode(chuongTrinhDaoTaoCode);
	}
	
}
