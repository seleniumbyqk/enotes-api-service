package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/notes")
public interface NotesControllerEndpoint {

	//No need to mention @RequestBody, @PathVariable, @RequestParam in implementation classes
	
	@PostMapping("/")
	@PreAuthorize("hasRole('USER')") 
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file)
			throws Exception;
	
	@GetMapping("/")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<?> getAllNotes();
	
	@GetMapping("/download/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')") 
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/user-notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllNotesByUser(
			// default value provide
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize);
	
	//Search notes
		@GetMapping("/search")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<?> searchNotes(
				// default value provide
				@RequestParam(name = "key", defaultValue = "") String key,
				@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
				@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize);
		
		// Data delete from database and recycle bin
		@GetMapping("/delete/{id}")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
		
		// Restore data in database
		@GetMapping("/restore/{id}")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
		
		// Data check in recycle bin which are deleted
		@GetMapping("/recycle-bin")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;
		
		// Data delete from recycle bin first soft delete and then hard delete
		@DeleteMapping("/delete/{id}")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
		
		// Delete all data
		@DeleteMapping("/delete-recycle")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<?> emptyUserRecycleBin() throws Exception;
		
		// Favorite notes
		@GetMapping("/fav/{noteId}")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception;
		
		// Un Favorite notes
		@DeleteMapping("/un-fav/{favNoteId}")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNoteId) throws Exception;
		
		// Get User Favorite note
		@GetMapping("/fav-note")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<?> getUserFavouriteNote() throws Exception;
				
		// copy notes
		@GetMapping("/copy/{id}") 
		@PreAuthorize("hasRole('USER')") 
		public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
}
