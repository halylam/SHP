package vn.shp.app.service;

import java.util.List;

import vn.shp.app.entity.PortalConfig;

public interface PortalConfigService {

	PortalConfig save(PortalConfig entity);

	PortalConfig findOne(Long id);

	PortalConfig find(String system, String configName);

	List<PortalConfig> findAll();
}
