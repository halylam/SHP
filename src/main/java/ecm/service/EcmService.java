package ecm.service;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EcmService {

	Document upload(MultipartFile file, String toDirectory, Map<String, Object> properties) throws IOException;

	void update(String documentId, Map<String, Object> newProperties);

	void update(String documentId, MultipartFile file) throws IOException;
	
	Document download(String objectId);

	Folder createFolder(String folderName, String parentDirectory);
	
	Folder getFolder(String directory);
	
	Folder getFolderOrCreateIfNotExixt(String directory);
	
	void download(String objectId, String toDirectory);
	
	List<Document> searchAll();

}
