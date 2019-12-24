package tk.brabotim.overqueue.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tk.brabotim.overqueue.exceptions.FileStorageException;
import tk.brabotim.overqueue.payload.UploadFileResponse;
import tk.brabotim.overqueue.service.FileStorageService;

@Controller
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileStorageService fileStorageService;

	@GetMapping("admin/uploadmenu")
	public ModelAndView UploadPage(Model model) {

		return new ModelAndView("admin/uploadmenu");
	}

	@PostMapping("/admin/upload")
	public ModelAndView uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName;

		try {
			fileName = fileStorageService.storeFile(file);

		} catch (FileStorageException e) {
			logger.info(e.getMessage());
			return new ModelAndView("redirect:uploadmenu?error");
		}

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(fileName)
				.toUriString();

		new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
		return new ModelAndView("redirect:uploadmenu?success");
	}

	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = fileStorageService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
