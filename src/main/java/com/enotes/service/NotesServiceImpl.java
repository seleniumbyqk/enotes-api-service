package com.enotes.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesDto.CategoryDto;
import com.enotes.entity.Category;
import com.enotes.entity.Notes;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.repository.CategoryRepository;
import com.enotes.repository.NotesRepository;

@Service
public class NotesServiceImpl implements NotesService{

	
	@Autowired
	private NotesRepository notesRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public boolean saveNotes(NotesDto notesDto) throws Exception {
		// TODO Auto-generated method stub
		
		//Category validation - check category id is present or not in database
		//Integer categoryId = notesDto.getCategory().getId();
		checkCategoryExist(notesDto.getCategory());
		
		//First convert notesdto to notes
		//Notes notes = modelMapper.map(notesDto, Notes.class);
		
		
		//Manual
		 Notes notes = new Notes();
	        notes.setTitle(notesDto.getTitle());
	        notes.setDescription(notesDto.getDescription());

	        // ✅ MANUAL RELATIONSHIP MAPPING (FIX)
	        if (notesDto.getCategory() != null && notesDto.getCategory().getId() != null) {
	            Category category = new Category();
	            category.setId(notesDto.getCategory().getId());
	            notes.setCategory(category);
	        }
		
		
		//Save in database
		Notes saveNotes = notesRepository.save(notes);
		
		//Check notes are empty or not
		if(!ObjectUtils.isEmpty(saveNotes))
		{
			return true;
		}
		
		return false;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		// TODO Auto-generated method stub
		
		categoryRepository.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Category id is invalid"));
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
		// TODO Auto-generated method stub
		
		//First find all notes
		//List<Notes> notes = notesRepository.findAll();
		
		//Convert from category to categoryDto and into list
		//List<NotesDto> notesDtoList = notes.stream().map(note -> modelMapper.map(note, NotesDto.class)).toList();
		
		//return notesDtoList;
		
		//write all in one line 
		return notesRepository.findAll().stream().map(note -> modelMapper.map(note, NotesDto.class)).toList();
	}

	
	
}
