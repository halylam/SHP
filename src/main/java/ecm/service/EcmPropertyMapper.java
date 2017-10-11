package ecm.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface EcmPropertyMapper {
	Map<String, Object> mapProperties(Object o);
	Map<String, Object> mapProperties(Object o, MultipartFile file);
	Map<String, Object> mapProperties(MultipartFile file);
}
