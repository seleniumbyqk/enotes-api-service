package com.enotes.endpoint;

import static com.enotes.util.Constants.DEFAULT_KEY_VALUE;
import static com.enotes.util.Constants.DEFAULT_PAGE_NO;
import static com.enotes.util.Constants.DEFAULT_PAGE_SIZE;
import static com.enotes.util.Constants.ROLE_ADMIN_USER;
import static com.enotes.util.Constants.ROLE_USER;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NotesDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notes", description = "All the Notes OPeration APIs")
@RequestMapping("/api/v1/notes")
public interface NotesControllerEndpoint {

	//No need to mention @RequestBody, @PathVariable, @RequestParam in implementation classes
	
	@Operation(summary = "Save Notes Endpoint", tags = {"Notes", "User"}, description = "User Save Notes")
	//@PostMapping("/")
	@PostMapping(value = "/", consumes = "multipart/form-data")
	//@PreAuthorize("hasRole('USER')") 
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNotes(@RequestParam 
			@Parameter(description = "Json string notes", required = true,
			content = @Content(schema = @Schema(implementation = NotesDto.class)))
			String notes, @RequestParam(required = false) MultipartFile file)
			throws Exception;
	
	@Operation(summary = "Get all Notes Endpoint", tags = {"Notes", "User"}, description = "Get All Notes Admin")
	@GetMapping("/")
	//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getAllNotes();
	
	@Operation(summary = "Download uploaded files Endpoint", tags = {"Notes", "User"}, description = "Download files")
	@GetMapping("/download/{id}")
	//@PreAuthorize("hasAnyRole('USER', 'ADMIN')") 
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Get All Notes By User Endpoint", tags = {"Notes", "User"}, description = "Get All Notes For User")
	@GetMapping("/user-notes")
	//@PreAuthorize("hasRole('USER')")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesByUser(
			// default value provide
			//@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			//@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize
			@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
	
	//Search notes
	@Operation(summary = "Search Notes Endpoint", tags = {"Notes", "User"}, description = "User Search Notes")
	@GetMapping("/search")
	//@PreAuthorize("hasRole('USER')")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> searchNotes(
			// default value provide
			//@RequestParam(name = "key", defaultValue = "") String key,
			//@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			//@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize
			@RequestParam(name = "key", defaultValue = DEFAULT_KEY_VALUE) String key,
			@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,				
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
		
	// Data delete from database and recycle bin
	@Operation(summary = "Delete Notes Endpoint", tags = {"Notes", "User"}, description = "Delete Notes By User")
	@GetMapping("/delete/{id}")
	//@PreAuthorize("hasRole('USER')")		
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
		
	// Restore data in database
	@Operation(summary = "Restore Deleted Notes Endpoint", tags = {"Notes", "User"}, description = "Restore Deleted Notes From Recycle Bin")
	@GetMapping("/restore/{id}")
	//@PreAuthorize("hasRole('USER')")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
		
	// Data check in recycle bin which are deleted
	@Operation(summary = "Get Notes From Recycle Bin Endpoint", tags = {"Notes", "User"}, description = "Get Notes From Recycle Bin")
	@GetMapping("/recycle-bin")
	//@PreAuthorize("hasRole('USER')")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;
		
	// Data delete from recycle bin first soft delete and then hard delete
	@Operation(summary = "Hard Delete Notes Endpoint", tags = {"Notes", "User"}, description = "Hard Delete Notes")
	@DeleteMapping("/delete/{id}")
	//@PreAuthorize("hasRole('USER')")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
		
	// Delete all data
	@Operation(summary = "Empty User Recycle Bin Endpoint", tags = {"Notes", "User"}, description = "Empty User Recycle Bin")
	@DeleteMapping("/delete-recycle")
	//@PreAuthorize("hasRole('USER')")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyUserRecycleBin() throws Exception;
		
	// Favorite notes
	@Operation(summary = "Favourite Note Endpoint", tags = {"Notes", "User"}, description = "User Favourite Notes")
	@GetMapping("/fav/{noteId}")
	//@PreAuthorize("hasRole('USER')")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception;
		
	// Un Favorite notes
	@Operation(summary = "Un-Favourite Note Endpoint", tags = {"Notes", "User"}, description = "User Un-Favourite Notes")
	@DeleteMapping("/un-fav/{favNoteId}")
	//@PreAuthorize("hasRole('USER')")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNoteId) throws Exception;
		
	// Get User Favorite note
	@Operation(summary = "Get User Favorite Notes Endpoint", tags = {"Notes", "User"}, description = "Get User Favorite Notes Endpoint")
	@GetMapping("/fav-note")
	//@PreAuthorize("hasRole('USER')")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserFavouriteNote() throws Exception;
				
	// copy notes
	@Operation(summary = "Copy Notes Endpoint", tags = {"Notes", "User"}, description = "Copy Notes")
	@GetMapping("/copy/{id}") 
	//@PreAuthorize("hasRole('USER')") 
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
}
