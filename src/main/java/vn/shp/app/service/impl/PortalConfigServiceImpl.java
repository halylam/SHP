package vn.shp.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.shp.app.entity.PortalConfig;
import vn.shp.app.repository.PortalConfigRepository;
import vn.shp.app.service.PortalConfigService;

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

	public List<PortalConfig> findAll() {
		return portalConfigRepository.findAll();
	}
}
