package com.enotes.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.FavouritNoteDto;
import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesResponse;
import com.enotes.entity.FileDetails;

public interface NotesService {

	
	//public boolean saveNotes(NotesDto notesDto) throws Exception;
	
	public boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();

	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;

	public NotesResponse getAllNotesByUser(Integer pageNo, Integer pageSize);  //Integer userId - use when no logged in user

	//Search functionality
	public NotesResponse getAllNotesByUserSearch(Integer pageNo, Integer pageSize, String keyword);
	
	public void softDeleteNotes(Integer id) throws Exception;

	public void restoreNotes(Integer id) throws Exception;

	public List<NotesDto> getUserRecycleBinNotes();         // Integer userId - use when no logged in user

	public void hardDeleteNotes(Integer id) throws Exception;

	public void emptyRecycleBin();       //Integer userId - no need when logged in user
	
	public void favouriteNotes(Integer noteId) throws Exception;
	
	public void unFavouriteNotes(Integer noteId) throws Exception;
	
	public List<FavouritNoteDto> getUserFaouriteNotes() throws Exception;

	public Boolean copyNotes(Integer id) throws Exception;
}
