//package com.indekos.controller;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.List;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.google.api.services.drive.model.File;
//import com.indekos.services.AttachmentService;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@AllArgsConstructor
//@Slf4j
//@RestController
//@CrossOrigin
//@RequestMapping("/attachment")
//public class AttachmentController {
//	
//	private AttachmentService attachmentService;
//
//	@GetMapping({"/"})
//	public ResponseEntity<List<File>> listEverything() throws IOException, GeneralSecurityException {
//		List<File> files = attachmentService.listEverything();
//		return ResponseEntity.ok(files);
//	}
//
//	@GetMapping({"/list","/list/{parentId}"})
//	public ResponseEntity<List<File>> list(@PathVariable(required = false) String parentId) throws IOException, GeneralSecurityException {
//		List<File> files = attachmentService.listFolderContent(parentId);
//		return ResponseEntity.ok(files);
//	}
//
//	@GetMapping("/download/{id}")
//	public void download(@PathVariable String id, HttpServletResponse response) throws IOException, GeneralSecurityException {
//		attachmentService.downloadFile(id, response.getOutputStream());
//	}
//
//	@GetMapping("/directory/create")
//	public ResponseEntity<String> createDirecory(@RequestParam String path) throws Exception {
//		String parentId = attachmentService.getFolderId(path);
//		return ResponseEntity.ok("parentId: "+parentId);
//	}
//
//	@PostMapping(value = "/upload",
//			consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
//			produces = {MediaType.APPLICATION_JSON_VALUE} )
//	public ResponseEntity<String> uploadSingleFileExample4(@RequestBody MultipartFile file,@RequestParam(required = false) String path) {
//		log.info("Request contains, File: " + file.getOriginalFilename());
//		String fileId = attachmentService.uploadFile(file, path);
//		if(fileId == null){
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//		return ResponseEntity.ok("Success, FileId: "+ fileId);
//	}
//
//
//	@GetMapping("/delete/{id}")
//	public void delete(@PathVariable String id) throws Exception {
//		attachmentService.deleteFile(id);
//	}
//}
