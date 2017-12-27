package vn.shp.portal.service.impl;

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
import vn.shp.portal.entity.PortalUser;
import vn.shp.portal.model.PortalUserBean;
import vn.shp.portal.repository.PortalUserRepository;
import vn.shp.portal.service.PortalUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("PortalUserService")
public class PortalUserServiceImpl implements PortalUserService {

	@Autowired
	private PortalUserRepository portalUserRepo;

	@Override
	public List<PortalUser> findAll() {
		List<PortalUser> UserLst = portalUserRepo.findAll();
		return UserLst;
	}

	@Override
	@Transactional
	public void save(PortalUser portalUser) {
		portalUserRepo.save(portalUser);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		portalUserRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(PortalUser entity) {
		portalUserRepo.delete(entity);
	}

	@Override
	public PortalUser findOne(Long id) {
		return portalUserRepo.findOne(id);
	}

	@Override
	public PortalUser findByUsername(String username) {
		return portalUserRepo.findByUsername(username);
	}

	@Override
	public ModelAndView initSearch(PortalUserBean model, HttpServletRequest request) {
		
		String pageParam = request.getParameter(CoreConstant.CONST_PAGE);
		int page = 0;
		if(!StringUtils.isEmpty(pageParam) && !pageParam.equals("0")) {
			page = Integer.parseInt(pageParam) - 1;
		}
		int sizeOfPage = CoreConstant.DATA_TABLE_LIMIT;
		Pageable pageable = new PageRequest(page, sizeOfPage);
		
		if (model == null) {
			model = new PortalUserBean();
		}
		
		PortalUser entity = model.getEntity();
		Page<PortalUser> result;
		int count;
		if (entity != null && (!entity.getUsername().isEmpty() || !entity.getEmail().isEmpty() || !entity.getFullName().isEmpty())) {
			result = portalUserRepo.findBy(entity.getUsername(), entity.getEmail(), entity.getFullName(), pageable);
			count = portalUserRepo.findBy(entity.getUsername(), entity.getEmail(), entity.getFullName()).size();
		} else {
			result = portalUserRepo.findAll(pageable);
			count = portalUserRepo.findAll().size();
		}
		
		ModelAndView mav = new ModelAndView();
		PageWrapper<PortalUser> pageWrapper = new PageWrapper<PortalUser>(page + 1, sizeOfPage);
		
		pageWrapper.setDataAndCount(result.getContent(), count);
		mav.addObject("pageWrapper", pageWrapper);
		return mav;
		
	}


}
