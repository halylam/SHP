package ecm.service.impl;

import ecm.service.EcmPropertyMapper;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@Service
public class AlfrescoPropertyServiceImpl implements EcmPropertyMapper {

	@Override
	public Map<String, Object> mapProperties(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> mapProperties(Object o, MultipartFile file) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> mapProperties(MultipartFile file, String desc) {
		Map<String, Object> result = new HashMap<>();
		result.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
		result.put(PropertyIds.NAME, file.getOriginalFilename());
		result.put(PropertyIds.DESCRIPTION, desc);
		return result;
	}
	
}
