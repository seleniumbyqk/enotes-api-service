package com.enotes.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NotesDto;

public interface NotesService {

	
	//public boolean saveNotes(NotesDto notesDto) throws Exception;
	
	public boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();
}
