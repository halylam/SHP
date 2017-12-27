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
//import vn.shp.portal.entity.PortalBranch;
//import vn.shp.portal.model.PortalBranchModel;
//import vn.shp.portal.repository.PortalBranchRepository;
//import vn.shp.portal.service.PortalBranchService;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
//@Service("PortalBranchService")
//public class PortalBranchServiceImpl implements PortalBranchService {
//
//	@Autowired
//	private PortalBranchRepository portalBranchRepo;
//
//	@Override
//	public List<PortalBranch> findAll() {
//		List<PortalBranch> branchLst = portalBranchRepo.findAll();
//		return branchLst;
//	}
//
//	@Override
//	@Transactional
//	public void save(PortalBranch portalBranch) {
//		portalBranchRepo.save(portalBranch);
//	}
//
//	@Override
//	@Transactional
//	public void delete(Long id) {
//		portalBranchRepo.delete(id);
//	}
//
//	@Override
//	@Transactional
//	public void delete(PortalBranch entity) {
//		portalBranchRepo.delete(entity);
//	}
//
//	@Override
//	public PortalBranch findOne(Long id) {
//		return portalBranchRepo.findOne(id);
//	}
//
//	@Override
//	public ModelAndView initSearch(PortalBranchModel model, HttpServletRequest request) {
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
//			model = new PortalBranchModel();
//		}
//
//		PortalBranch entity = model.getEntity();
//		Page<PortalBranch> result;
//		int count;
//		if (entity != null && (!entity.getBranchName().isEmpty() || !entity.getBranchCode().isEmpty())) {
//			result = portalBranchRepo.findBy(entity.getBranchName(), entity.getBranchCode(), pageable);
//			count = portalBranchRepo.findBy(entity.getBranchName(), entity.getBranchCode()).size();
//		} else {
//			result = portalBranchRepo.findAll(pageable);
//			count = portalBranchRepo.findAll().size();
//		}
//
//		ModelAndView mav = new ModelAndView();
//		PageWrapper<PortalBranch> pageWrapper = new PageWrapper<PortalBranch>(page + 1, sizeOfPage);
//
//		pageWrapper.setDataAndCount(result.getContent(), count);
//		mav.addObject("pageWrapper", pageWrapper);
//		return mav;
//
//	}
//
//	@Override
//	public PortalBranch findByBranchCode(String branchCode) {
//		return portalBranchRepo.findByBranchCode(branchCode);
//	}
//
//}
