//package vn.shp.portal.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.ModelAndView;
//import vn.shp.portal.constant.CoreConstant;
//import vn.shp.portal.core.PageWrapper;
//import vn.shp.portal.entity.PortalSystem;
//import vn.shp.portal.model.PortalSystemModel;
//import vn.shp.portal.repository.PortalSystemRepository;
//import vn.shp.portal.service.PortalSystemService;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
//@Service("PortalSystemsService")
//public class PortalSystemServiceImpl implements PortalSystemService {
//
//	@Autowired
//	private PortalSystemRepository portalSystemRepo;
//
//	@Override
//	public List<PortalSystem> findAll() {
//		List<PortalSystem> systemLst = portalSystemRepo.findAll();
//		return systemLst;
//	}
//
//	@Override
//	@Transactional
//	public void save(PortalSystem portalSystem) {
//		portalSystemRepo.save(portalSystem);
//	}
//
//	@Override
//	@Transactional
//	public void delete(Long id) {
//		portalSystemRepo.delete(id);
//	}
//
//	@Override
//	public PortalSystem findOne(Long id) {
//		return portalSystemRepo.findOne(id);
//	}
//
//	@Override
//	public ModelAndView initSearch(PortalSystemModel model, HttpServletRequest request) {
//
//		String pageParam = request.getParameter(CoreConstant.CONST_PAGE);
//		int page = 0;
//		if(!StringUtils.isEmpty(pageParam) && !pageParam.equals("0")) {
//			page = Integer.parseInt(pageParam) - 1;
//		}
//		int sizeOfPage = CoreConstant.DATA_TABLE_LIMIT;
//		Pageable pageable = new PageRequest(page, sizeOfPage);
//
//		if (model == null) {
//			model = new PortalSystemModel();
//		}
//
//		PortalSystem entity = model.getEntity();
//		Page<PortalSystem> result;
//		int count;
//		if (entity != null && (!entity.getSystemCode().isEmpty() || !entity.getSystemName().isEmpty() || !entity.getStatus().isEmpty())) {
//			result = portalSystemRepo.findBy(entity.getSystemCode(), entity.getSystemName(), entity.getStatus(), pageable);
//			count = portalSystemRepo.findBy(entity.getSystemCode(), entity.getSystemName(), entity.getStatus()).size();
//		} else {
//			result = portalSystemRepo.findAll(pageable);
//			count = portalSystemRepo.findAll().size();
//		}
//
//		ModelAndView mav = new ModelAndView();
//		PageWrapper<PortalSystem> pageWrapper = new PageWrapper<PortalSystem>(page + 1, sizeOfPage);
//
//		pageWrapper.setDataAndCount(result.getContent(), count);
//		mav.addObject("pageWrapper", pageWrapper);
//		return mav;
//
//	}
//
//}
