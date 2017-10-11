package ecm.service.impl;

import ecm.service.EcmService;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.client.util.FileUtils;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.definitions.PropertyDefinition;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.impl.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.shp.app.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class AlfrescoCmisServiceImpl implements EcmService {

	private String username;

	private String password;

	private String atomUrl;

	private String repositoryId;

	private Session session;

	@SuppressWarnings("unused")
	private AlfrescoCmisServiceImpl() {
	};

	public AlfrescoCmisServiceImpl(String username, String password, String atomUrl, String repositoryId) {
		super();
		this.username = username;
		this.password = password;
		this.atomUrl = atomUrl;
		this.repositoryId = repositoryId;

		this.session = createSession();
	}

	public Session getSession() {
		if (session == null)
			session = createSession();
		return session;
	}

	public Session createSession() {
		SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
		Map<String, String> params = new HashMap<String, String>();
		params.put(SessionParameter.USER, username);
		params.put(SessionParameter.PASSWORD, password);
		params.put(SessionParameter.ATOMPUB_URL, atomUrl);
		params.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
		params.put(SessionParameter.COMPRESSION, "true");
		params.put(SessionParameter.CACHE_TTL_OBJECTS, "0");
		params.put(SessionParameter.REPOSITORY_ID, repositoryId);

		Session session = sessionFactory.createSession(params);
		return session;
	}

	@Override
	public Document upload(MultipartFile file, String toDirectory, Map<String, Object> properties) throws IOException {
		String path = getPath(file, toDirectory);

		InputStream is = null;
		ContentStream cs = null;
		try {
			is = file.getInputStream();
			Folder parent = getFolderOrCreatedIfNotExist(toDirectory);
			Document result = null;
			try {
				result = (Document) session.getObjectByPath(path);
				cs = getSession().getObjectFactory().createContentStream(file.getOriginalFilename(), file.getSize(), result.getContentStreamMimeType(), is);
				result.setContentStream(cs, true);

				// re-get the document since alfresco don't return new document when we update content stream
				result = (Document) session.getObjectByPath(path);
			} catch (CmisObjectNotFoundException ex) {
				cs = getSession().getObjectFactory().createContentStream(file.getOriginalFilename(), file.getSize(),
						"application/octet-stream", is);
				result = parent.createDocument(properties, cs, VersioningState.MAJOR);
			}
			return result;

		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(cs);
		}
	}

	private String getPath(MultipartFile file, String toDirectory) {
		String path;
		if (toDirectory.endsWith("/")) {
			path = toDirectory + file.getOriginalFilename();
		} else {
			path = toDirectory + "/" + file.getOriginalFilename();
		}
		return path;
	}

	@Override
	public void update(String objectId, Map<String, Object> newProperties) {
		Document document = getDocument(objectId);
		document.updateProperties(newProperties);
	}

	public Document getDocument(String objectId) {
		return (Document) session.getObject(session.createObjectId(objectId));
	}

	@Override
	public void update(String objectId, MultipartFile file) throws IOException {
		Document document = getDocument(objectId);
		InputStream is = null;
		ContentStream cs = null;
		try {
			is = file.getInputStream();
			cs = session.getObjectFactory().createContentStream(file.getName(), file.getSize(),
					document.getContentStreamMimeType(), is);
			document.setContentStream(cs, true);
		} finally {
			IOUtils.closeQuietly(cs);
			IOUtils.closeQuietly(is);
		}
	}

	public List<Document> search(Map<String, Object> conditions) {
		String myType = "cmis:document";
		QueryStatement queryStatement = session.createQueryStatement("select ? from ?");
		queryStatement.setProperty(1, "cmis:document", "cmis:name");
		queryStatement.setType(2, myType);

		ItemIterable<QueryResult> results = session.query(queryStatement.toQueryString(), true);
		String objectIdQueryName = getObjectIdQueryName();

		List<Document> documents = new ArrayList<>();
		for (QueryResult result : results) {
			String objectId = result.getPropertyValueByQueryName(objectIdQueryName);
			documents.add(getDocument(objectId));
		}

		return documents;
	}

	private String getObjectIdQueryName() {
		ObjectType type = session.getTypeDefinition("cmis:document");
		PropertyDefinition<?> objectIdDefinition = type.getPropertyDefinitions().get(PropertyIds.OBJECT_ID);
		String objectIdQueryName = objectIdDefinition.getQueryName();
		return objectIdQueryName;
	}

	private Folder getFolderOrCreatedIfNotExist(String toDirectory) {
		Folder folder = (Folder) session.getObjectByPath(toDirectory);
		return folder;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAtomUrl() {
		return atomUrl;
	}

	public void setAtomUrl(String atomUrl) {
		this.atomUrl = atomUrl;
	}

	public String getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}

	@Override
	public Document download(String objectId) {
		return getDocument(objectId);
	}
	
	@Override
	public void download(String objectId, String toDirectory) {
		try {
			FileUtils.download(download(objectId), toDirectory + download(objectId).getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Folder createFolder(String folderName, String parentDirectory) {
		return FileUtils.createFolder(parentDirectory, folderName, null, session);
	}

	@Override
	public Folder getFolder(String directory) {
		return FileUtils.getFolder(directory, session);
	}

	@Override
	public Folder getFolderOrCreateIfNotExixt(String directory) {
		String directoryBase = "/Sites/vccb-workflow/documentlibrary/";
		
		Folder folder;
		try {
			folder = getFolder(directory);
		} catch (Exception e) {
			char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			StringBuilder sb = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < 8; i++) {
			    char c = chars[random.nextInt(chars.length)];
			    sb.append(c);
			}
			folder = createFolder(sb.toString(), directoryBase);
		}
		
		return folder;
	}
	
	@Override
	public List<Document> searchAll() {
		String myType = "cmis:document";
		QueryStatement queryStatement = session.createQueryStatement("select ? from ?");
		queryStatement.setProperty(1, "cmis:document", "cmis:name");
		queryStatement.setType(2, myType);

		ItemIterable<QueryResult> results = session.query("SELECT * FROM cmis:document", false);
		String objectIdQueryName = getObjectIdQueryName();

		List<Document> documents = new ArrayList<>();
		for (QueryResult result : results) {
			String objectId = result.getPropertyValueByQueryName(objectIdQueryName);
			if (Utils.isNotNullOrEmpty(objectId)) {
				documents.add(getDocument(objectId));
			}
		}

		return documents;
	}

}
