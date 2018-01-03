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
import vn.shp.portal.entity.PortalRole;
import vn.shp.portal.model.PortalRoleModel;
import vn.shp.portal.repository.PortalRoleRepository;
import vn.shp.portal.service.PortalRoleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("portalRoleService")
public class PortalRoleServiceImpl implements PortalRoleService {

	@Autowired
	private PortalRoleRepository portalRoleRepo;

	@Override
	public List<PortalRole> findAll() {
		return portalRoleRepo.findAll();
	}

	@Override
	@Transactional
	public void save(PortalRole entity) {
		portalRoleRepo.save(entity);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		portalRoleRepo.delete(id);
	}
	
	@Override
	public PortalRole findOne(Long id) {
		return portalRoleRepo.findOne(id);
	}
	
	@Override
	public ModelAndView initSearch(PortalRoleModel model, HttpServletRequest request) {

		String pageParam = request.getParameter(CoreConstant.CONST_PAGE);
		int page = 0;
		if(!StringUtils.isEmpty(pageParam) && !pageParam.equals("0")) {
			page = Integer.parseInt(pageParam) - 1;
		}
		int sizeOfPage = CoreConstant.DATA_TABLE_LIMIT;
		Pageable pageable = new PageRequest(page, sizeOfPage);

		if (model == null) {
			model = new PortalRoleModel();
		}

		PortalRole entity = model.getEntity();
		Page<PortalRole> result;
		int count;
		if (entity != null && (!entity.getRoleCode().isEmpty() || !entity.getRoleName().isEmpty() || !entity.getStatus().isEmpty())) {
			result = portalRoleRepo.findBy(entity.getRoleCode(), entity.getRoleName(), entity.getStatus(), pageable);
			count = portalRoleRepo.findBy(entity.getRoleCode(), entity.getRoleName(), entity.getStatus()).size();
		} else {
			result = portalRoleRepo.findAll(pageable);
			count = portalRoleRepo.findAll().size();
		}

		ModelAndView mav = new ModelAndView();
		PageWrapper<PortalRole> pageWrapper = new PageWrapper<PortalRole>(page + 1, sizeOfPage);

		pageWrapper.setDataAndCount(result.getContent(), count);
		mav.addObject("pageWrapper", pageWrapper);
		return mav;

	}
}
