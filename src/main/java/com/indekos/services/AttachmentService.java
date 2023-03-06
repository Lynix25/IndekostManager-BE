//package com.indekos.services;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.google.api.client.http.InputStreamContent;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.model.File;
//import com.google.api.services.drive.model.FileList;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@AllArgsConstructor
//@Slf4j
//@Service
//public class AttachmentService {
//	
//	private ObjectStorageService objectStorageService;
//
//	public List<File> listEverything() throws IOException, GeneralSecurityException {
//		// Print the names and IDs for up to 10 files.
//		FileList result = objectStorageService.getInstance().files().list()
//				.setPageSize(10)
//				.setFields("nextPageToken, files(id, name)")
//				.execute();
//		return result.getFiles();
//	}
//
//	public List<File> listFolderContent(String parentId) throws IOException, GeneralSecurityException {
//		if (parentId == null) {
//			parentId = "root";
//		}
//		String query = "'" + parentId + "' in parents";
//		FileList result = objectStorageService.getInstance().files().list()
//				.setQ(query)
//				.setPageSize(10)
//				.setFields("nextPageToken, files(id, name)")
//				.execute();
//		return result.getFiles();
//	}
//
//	public void downloadFile(String id, OutputStream outputStream) throws IOException, GeneralSecurityException {
//		if (id != null) {
//			String fileId = id;
//			objectStorageService.getInstance().files().get(fileId).executeMediaAndDownloadTo(outputStream);
//		}
//	}
//
//	public void deleteFile(String fileId) throws Exception {
//		objectStorageService.getInstance().files().delete(fileId).execute();
//	}
//
//	public String uploadFile(MultipartFile file, String filePath) {
//		try {
//			String folderId = getFolderId(filePath);
//			if (null != file) {
//				File fileMetadata = new File();
//				fileMetadata.setParents(Collections.singletonList(folderId));
//				fileMetadata.setName(file.getOriginalFilename());
//				File uploadFile = objectStorageService.getInstance()
//						.files()
//						.create(fileMetadata, new InputStreamContent(
//								file.getContentType(),
//								new ByteArrayInputStream(file.getBytes()))
//						)
//						.setFields("id").execute();
//				return uploadFile.getId();
//			}
//		} catch (Exception e) {
//			log.error("Error: ", e);
//		}
//		return null;
//	}
//
//	public String getFolderId(String path) throws Exception {
//		String parentId = null;
//		String[] folderNames = path.split("/");
//
//		Drive driveInstance = objectStorageService.getInstance();
//		for (String name : folderNames) {
//			parentId = findOrCreateFolder(parentId, name, driveInstance);
//		}
//		return parentId;
//	}
//
//	private String findOrCreateFolder(String parentId, String folderName, Drive driveInstance) throws Exception {
//		String folderId = searchFolderId(parentId, folderName, driveInstance);
//		// Folder already exists, so return id
//		if (folderId != null) {
//			return folderId;
//		}
//		//Folder dont exists, create it and return folderId
//		File fileMetadata = new File();
//		fileMetadata.setMimeType("application/vnd.google-apps.folder");
//		fileMetadata.setName(folderName);
//
//		if (parentId != null) {
//			fileMetadata.setParents(Collections.singletonList(parentId));
//		}
//		return driveInstance.files().create(fileMetadata)
//				.setFields("id")
//				.execute()
//				.getId();
//	}
//
//	private String searchFolderId(String parentId, String folderName, Drive service) throws Exception {
//		String folderId = null;
//		String pageToken = null;
//		FileList result = null;
//
//		File fileMetadata = new File();
//		fileMetadata.setMimeType("application/vnd.google-apps.folder");
//		fileMetadata.setName(folderName);
//
//		do {
//			String query = " mimeType = 'application/vnd.google-apps.folder' ";
//			if (parentId == null) {
//				query = query + " and 'root' in parents";
//			} else {
//				query = query + " and '" + parentId + "' in parents";
//			}
//			result = service.files().list().setQ(query)
//					.setSpaces("drive")
//					.setFields("nextPageToken, files(id, name)")
//					.setPageToken(pageToken)
//					.execute();
//
//			for (File file : result.getFiles()) {
//				if (file.getName().equalsIgnoreCase(folderName)) {
//					folderId = file.getId();
//				}
//			}
//			pageToken = result.getNextPageToken();
//		} while (pageToken != null && folderId == null);
//
//		return folderId;
//	}
//}
