package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.shp.portal.entity.PortalConfig;
import vn.shp.portal.repository.PortalConfigRepository;
import vn.shp.portal.service.PortalConfigService;

@Service("portalConfigService")
public class PortalConfigServiceImpl implements PortalConfigService {

	@Autowired
	PortalConfigRepository portalConfigRepository;

	@Override
	public PortalConfig save(PortalConfig entity) {
		return portalConfigRepository.save(entity);
	}

	@Override
	public PortalConfig findOne(Long id) {
		return portalConfigRepository.findOne(id);
	}

	@Override
	public PortalConfig find(String system, String configName) {
		return portalConfigRepository.find(system, configName);
	}
}
