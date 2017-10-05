package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.portal.entity.PortalTitle;
import vn.shp.portal.repository.PortalTitleRepository;
import vn.shp.portal.service.PortalTitleService;

import java.util.List;

@Service("PortalTitleService")
public class PortalTitleServiceImpl implements PortalTitleService {

	@Autowired
	private PortalTitleRepository portalTitleRepo;
	
	@Override
	public List<PortalTitle> findAll() {
		return portalTitleRepo.findAll();
	}

	@Override
	@Transactional
	public void save(PortalTitle entity) {
		portalTitleRepo.save(entity);
	}
	
	@Override
	public List<PortalTitle> findByData(PortalTitle portalTitle) {
		return portalTitleRepo.findByTitleCodeOrTitleNameOrStatus(portalTitle.getTitleCode(), portalTitle.getTitleName(), portalTitle.getStatus());
	}
	
	@Override
	public PortalTitle findOne(Long id) {
		return portalTitleRepo.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long titleId) {
		portalTitleRepo.delete(titleId);
	}

	@Override
	public PortalTitle findByTitleCode(String titleCode) {
		return portalTitleRepo.findByTitleCode(titleCode);
	}

}
