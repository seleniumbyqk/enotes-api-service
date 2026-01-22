package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.FavouritNoteDto;
import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesResponse;
import com.enotes.entity.FileDetails;
import com.enotes.service.NotesService;
import com.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

	@Autowired
	private NotesService notesService;

	/*
	 * //To convert json to object class - @RequestBody NotesDto notesDto
	 * 
	 * @PostMapping("/") public ResponseEntity<?> saveNotes(@RequestBody NotesDto
	 * notesDto) throws Exception { Boolean saveNotes =
	 * notesService.saveNotes(notesDto);
	 * 
	 * //If true if(saveNotes) { //Give message return
	 * CommonUtil.createBuildResponseMessage("Notes saved success",
	 * HttpStatus.CREATED); }
	 * 
	 * return CommonUtil.createErrorResponseMessage("Notes not saved",
	 * HttpStatus.INTERNAL_SERVER_ERROR);
	 * 
	 * }
	 */

	// To save file in folder and save name in database only
	@PostMapping("/")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file)
			throws Exception {
		Boolean saveNotes = notesService.saveNotes(notes, file);

		// If true
		if (saveNotes) {
			// Give message
			return CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
		}

		return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping("/")
	public ResponseEntity<?> getAllNotes() {
		List<NotesDto> notes = notesService.getAllNotes();

		// If true
		if (CollectionUtils.isEmpty(notes)) {
			// Give message
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);

	}

	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {
		FileDetails fileDetails = notesService.getFileDetails(id);

		byte[] data = notesService.downloadFile(fileDetails);

		HttpHeaders headers = new HttpHeaders();

		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));

		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

		return ResponseEntity.ok().headers(headers).body(data);
	}

	@GetMapping("/user-notes")
	public ResponseEntity<?> getAllNotesByUser(
			// default value provide
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
		Integer userId = 2;

		NotesResponse notes = notesService.getAllNotesByUser(userId, pageNo, pageSize);

		/*
		 * //If true if(CollectionUtils.isEmpty(notes)) { //Give message return
		 * ResponseEntity.noContent().build(); }
		 */
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);

	}

	// Data delete from database and recycle bin
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception {
		notesService.softDeleteNotes(id);

		return CommonUtil.createBuildResponseMessage("Delete success", HttpStatus.OK);
	}

	// Restore data in database
	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {
		notesService.restoreNotes(id);

		return CommonUtil.createBuildResponseMessage("Delete Restore success", HttpStatus.OK);
	}

	// Data check in recycle bin which are deleted
	@GetMapping("/recycle-bin")
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception {
		Integer userId = 2;

		List<NotesDto> notes = notesService.getUserRecycleBinNotes(userId);

		if (CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponseMessage("Notes not available", HttpStatus.OK);
		}

		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}

	// Data delete from recycle bin first soft delete and then hard delete
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {
		notesService.hardDeleteNotes(id);

		return CommonUtil.createBuildResponseMessage("Delete success", HttpStatus.OK);
	}

	// Delete all data
	@DeleteMapping("/delete-recycle")
	public ResponseEntity<?> emptyRecycleBin() throws Exception {

		// Currently logged in user
		Integer userId = 2;
		
		notesService.emptyRecycleBin(userId);

		return CommonUtil.createBuildResponseMessage("Delete success", HttpStatus.OK);
	}

	// Favorite notes
	@GetMapping("/fav/{noteId}")
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception {

		// Currently logged in user
		//Integer userId = 2;
		
		notesService.favouriteNotes(noteId);

		return CommonUtil.createBuildResponseMessage("Notes added favorite", HttpStatus.CREATED);
	}
	
	// Un Favorite notes
		@DeleteMapping("/un-fav/{favNoteId}")
		public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNoteId) throws Exception {

			// Currently logged in user
			//Integer userId = 2;
			notesService.unFavouriteNotes(favNoteId);

			return CommonUtil.createBuildResponseMessage("Remove favorite", HttpStatus.OK);
		}
		
		// Get User Favorite note
		@GetMapping("/fav-note")
		public ResponseEntity<?> getUserFavouriteNote() throws Exception {

			// Currently logged in user
			Integer userId = 2;
			List<FavouritNoteDto> userFaouriteNotes = notesService.getUserFaouriteNotes();
			
			if(CollectionUtils.isEmpty(userFaouriteNotes))
			{
				return ResponseEntity.noContent().build();
			}

			return CommonUtil.createBuildResponse(userFaouriteNotes, HttpStatus.OK);
		}
		
		
		// copy notes
		@GetMapping("/copy/{id}")
		public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception {

			// Currently logged in user
			//Integer userId = 2;
			
			Boolean copyNotes = notesService.copyNotes(id);
			
			if(copyNotes)
			{
				return CommonUtil.createBuildResponseMessage("copied success", HttpStatus.CREATED);
			}

			return CommonUtil.createErrorResponseMessage("copy failed. Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
}
