package com.enotes.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.FavouritNoteDto;
import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesResponse;
import com.enotes.endpoint.NotesControllerEndpoint;
import com.enotes.entity.FileDetails;
import com.enotes.service.NotesService;
import com.enotes.util.CommonUtil;

@RestController
//@RequestMapping("/api/v1/notes")
public class NotesController implements NotesControllerEndpoint{

	/*
	//field injection
	@Autowired
	private NotesService notesService;
    */
	
	//constructor injection
	private final NotesService notesService;
	
	public NotesController(NotesService notesService)
	{
		this.notesService = notesService;
	}
	
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
	//@PostMapping("/")
	//@PreAuthorize("hasRole('USER')") 
	@Override
	public ResponseEntity<?> saveNotes(String notes,  MultipartFile file)
			throws Exception {
		boolean saveNotes = notesService.saveNotes(notes, file);

		// If true
		if (saveNotes) {
			// Give message
			return CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
		}

		return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	//@GetMapping("/")
	//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@Override
	public ResponseEntity<?> getAllNotes() {
		List<NotesDto> notes = notesService.getAllNotes();

		// If true
		if (CollectionUtils.isEmpty(notes)) {
			// Give message
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);

	}

	//@GetMapping("/download/{id}")
	//@PreAuthorize("hasAnyRole('USER', 'ADMIN')") 
	@Override
	public ResponseEntity<?> downloadFile(Integer id) throws Exception {
		FileDetails fileDetails = notesService.getFileDetails(id);

		byte[] data = notesService.downloadFile(fileDetails);

		HttpHeaders headers = new HttpHeaders();

		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));

		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

		return ResponseEntity.ok().headers(headers).body(data);
	}

	//@GetMapping("/user-notes")
	//@PreAuthorize("hasRole('USER')")
	@Override
	public ResponseEntity<?> getAllNotesByUser(
			// default value provide
			Integer pageNo,
			Integer pageSize) {
		
		//Static user id
		//Integer userId = 2;
		
		//logged in user id
		//Integer userId = CommonUtil.getLoggedInUser().getId();

		NotesResponse notes = notesService.getAllNotesByUser(pageNo, pageSize);   //userId - when no logged in user

		/*
		 * //If true if(CollectionUtils.isEmpty(notes)) { //Give message return
		 * ResponseEntity.noContent().build(); }
		 */
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);

	}
	
	//Search notes
	//@GetMapping("/search")
	//@PreAuthorize("hasRole('USER')")
	@Override
	public ResponseEntity<?> searchNotes(
			// default value provide
			String key,
			Integer pageNo,
			Integer pageSize) {
		
		//Static user id
		//Integer userId = 2;
		
		//logged in user id
		//Integer userId = CommonUtil.getLoggedInUser().getId();

		NotesResponse notes = notesService.getAllNotesByUserSearch(pageNo, pageSize, key);   //userId - when no logged in user

		/*
		 * //If true if(CollectionUtils.isEmpty(notes)) { //Give message return
		 * ResponseEntity.noContent().build(); }
		 */
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);

	}
	

	// Data delete from database and recycle bin
	//@GetMapping("/delete/{id}")
	//@PreAuthorize("hasRole('USER')")
	@Override
	public ResponseEntity<?> deleteNotes(Integer id) throws Exception {
		notesService.softDeleteNotes(id);

		return CommonUtil.createBuildResponseMessage("Delete Notes success", HttpStatus.OK);
	}

	// Restore data in database
	//@GetMapping("/restore/{id}")
	//@PreAuthorize("hasRole('USER')")
	@Override
	public ResponseEntity<?> restoreNotes(Integer id) throws Exception {
		notesService.restoreNotes(id);

		return CommonUtil.createBuildResponseMessage("Delete Restore success", HttpStatus.OK);
	}

	// Data check in recycle bin which are deleted
	//@GetMapping("/recycle-bin")
	//@PreAuthorize("hasRole('USER')")
	@Override
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception {
		
		//Static user id
		//Integer userId = 2;
		
		//logged in user id
		//Integer userId = CommonUtil.getLoggedInUser().getId();

		List<NotesDto> notes = notesService.getUserRecycleBinNotes();  // userId - use when no logged in user

		if (CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponseMessage("Notes not available", HttpStatus.OK);
		}

		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}

	// Data delete from recycle bin first soft delete and then hard delete
	//@DeleteMapping("/delete/{id}")
	//@PreAuthorize("hasRole('USER')")
	@Override
	public ResponseEntity<?> hardDeleteNotes(Integer id) throws Exception {
		notesService.hardDeleteNotes(id);

		return CommonUtil.createBuildResponseMessage("Delete hard notes success", HttpStatus.OK);
	}

	// Delete all data
	//@DeleteMapping("/delete-recycle")
	//@PreAuthorize("hasRole('USER')")
	@Override
	public ResponseEntity<?> emptyUserRecycleBin() throws Exception {

		//Static  user id
		//Integer userId = 2;
		
		//logged in user id
		//Integer userId = CommonUtil.getLoggedInUser().getId();
		
		//For static user id
		//notesService.emptyRecycleBin(userId);
		
		//For logged in user
		notesService.emptyRecycleBin();

		return CommonUtil.createBuildResponseMessage("Delete recycle bin success", HttpStatus.OK);
	}

	// Favorite notes
	//@GetMapping("/fav/{noteId}")
	//@PreAuthorize("hasRole('USER')")
	@Override
	public ResponseEntity<?> favouriteNote(Integer noteId) throws Exception {

		// Currently logged in user
		//Integer userId = 2;
		
		notesService.favouriteNotes(noteId);

		return CommonUtil.createBuildResponseMessage("Notes added favorite", HttpStatus.CREATED);
	}
	
	// Un Favorite notes
		//@DeleteMapping("/un-fav/{favNoteId}")
		//@PreAuthorize("hasRole('USER')")
	    @Override
		public ResponseEntity<?> unFavouriteNote(Integer favNoteId) throws Exception {

			// Currently logged in user
			//Integer userId = 2;
			notesService.unFavouriteNotes(favNoteId);

			return CommonUtil.createBuildResponseMessage("Remove favorite", HttpStatus.OK);
		}
		
		// Get User Favorite note
		//@GetMapping("/fav-note")
		//@PreAuthorize("hasRole('USER')")
	    @Override
		public ResponseEntity<?> getUserFavouriteNote() throws Exception {

			//Static user id
			//Integer userId = 2;
			
			//logged in user id
			//Integer userId = CommonUtil.getLoggedInUser().getId();
			
			List<FavouritNoteDto> userFaouriteNotes = notesService.getUserFaouriteNotes();
			
			if(CollectionUtils.isEmpty(userFaouriteNotes))
			{
				return ResponseEntity.noContent().build();
			}

			return CommonUtil.createBuildResponse(userFaouriteNotes, HttpStatus.OK);
		}
		
		
		// copy notes
		//@GetMapping("/copy/{id}") 
		//@PreAuthorize("hasRole('USER')") 
	    @Override
		public ResponseEntity<?> copyNotes(Integer id) throws Exception {

			//Currently logged in user 
			//Integer userId = 2; 
			

			//TODO: Need to check User Validation 

			Boolean copyNotes = notesService.copyNotes(id);
			
			if(copyNotes)
			{
				return CommonUtil.createBuildResponseMessage("copied success", HttpStatus.CREATED); 
			}

			return CommonUtil.createErrorResponseMessage("copy failed. Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
}
