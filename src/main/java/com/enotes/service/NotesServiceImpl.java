package com.enotes.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesDto.CategoryDto;
import com.enotes.dto.NotesDto.FilesDto;
import com.enotes.dto.NotesResponse;
import com.enotes.entity.Category;
import com.enotes.entity.FileDetails;
import com.enotes.entity.Notes;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.repository.CategoryRepository;
import com.enotes.repository.FileRepository;
import com.enotes.repository.NotesRepository;

import tools.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService{

	
	@Autowired
	private NotesRepository notesRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	/*
	//Save notes
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
*/
	
	private void checkCategoryExist(CategoryDto category) throws Exception {
		// TODO Auto-generated method stub
		
		categoryRepository.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Category id is invalid"));
		
	}

	
	//Save file
	@Override
	public boolean saveNotes(String notes, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		
		//json to object
		ObjectMapper object = new ObjectMapper();
		NotesDto notesDto = object.readValue(notes, NotesDto.class);
		
		notesDto.setIsDeleted(false);
		notesDto.setDeletedOn(null);
		
		//Check id is present or not
		Integer id = notesDto.getId();
		
		if(!ObjectUtils.isEmpty(id))
		{
			//Update notes
			updateNotes(notesDto, file);
		}
		
		
		
		//Check category exist or not
		checkCategoryExist(notesDto.getCategory());
		
		
		//Manual
		 Notes notesObj = new Notes();
		 notesObj.setTitle(notesDto.getTitle());
		 notesObj.setDescription(notesDto.getDescription());

	        // ✅ MANUAL RELATIONSHIP MAPPING (FIX)
	        if (notesDto.getCategory() != null && notesDto.getCategory().getId() != null) {
	            Category category = new Category();
	            category.setId(notesDto.getCategory().getId());
	            notesObj.setCategory(category);
	        }
		
	        /*
	      //Save file details
			FileDetails fileDetails = saveFileDetails(file);
			
			if(ObjectUtils.isEmpty(fileDetails))
			{
				notesobj.setFileDetails(fileDetails);
			}
			else
			{
				notesobj.setFileDetails(null);
			}
			*/
	        
		//Save in database
			
			// ✅ Save file
	        if (file != null && !file.isEmpty()) {
	            FileDetails fileDetail = saveFileDetails(file);
	            notesObj.setFileDetails(fileDetail);
	        }
	        else
	        {
	        	if(ObjectUtils.isEmpty(notesDto.getId()))
	        	{
	        		notesObj.setFileDetails(null);
	        	}
	        	
	        }
	        
		Notes saveNotes = notesRepository.save(notesObj);
		
		
		//Check notes are empty or not
		if(!ObjectUtils.isEmpty(saveNotes))
		{
			return true;
		}
		
		return false;
	}
	
	
	private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		
		Notes existNotes = notesRepository.findById(notesDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Notes id"));
		
		
		//If user not choosen any file at update time
		if(ObjectUtils.isEmpty(file))
		{
			FileDetails fileDetails = existNotes.getFileDetails();
			
			notesDto.setFileDetails(modelMapper.map(fileDetails, FilesDto.class));
		}
		
	}


	private FileDetails saveFileDetails(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
		
		
		//Check file is empty or not
		if(!ObjectUtils.isEmpty(file) && !file.isEmpty())
		{
			
			String originalFileName = file.getOriginalFilename();
			
			String extension = FilenameUtils.getExtension(originalFileName);
			
			List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "png", "docs", "txt", "jpeg");
			
			if(!extensionAllow.contains(extension))
			{
				throw new IllegalArgumentException("Invalid file format ! upload only .pdf, .xlsx, .jpg, .png");
			}
			
	        if (originalFileName == null) {
	            throw new RuntimeException("Invalid file name");
	        }
	        
			//File details object
			FileDetails fileDetails = new FileDetails();
			
			//String originalFileName = fileDetails.getOriginalFileName();
			
			
			fileDetails.setOriginalFileName(originalFileName);
			
			fileDetails.setDisplayFileName(getDisplayName(originalFileName));
			
			String randomString = UUID.randomUUID().toString();
			
			//String extension = FilenameUtils.getExtension(originalFileName);
			
			String uploadFileName = randomString + "." + extension;
			
			fileDetails.setUploadFileName(uploadFileName);
			
			fileDetails.setFileSize(file.getSize());
			
			File folder = new File(uploadPath);
			
			if(!folder.exists())
			{
				folder.mkdirs();
			}
			
			//Path - enotes-api-service/notes/java.pdf
			String storePath = uploadPath.concat(uploadFileName);
			 //String storePath = uploadPath + File.separator + uploadFileName;
			
			fileDetails.setPath(storePath);
			
			//Upload path
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			
			//If 0 then file not upload
			if(upload != 0)
			{
				FileDetails saveFileDetails = fileRepository.save(fileDetails);
				
				return saveFileDetails;
			}
		}
		
		return null;
	}


	private String getDisplayName(String originalFileName) {
		// TODO Auto-generated method stub
		
		//Original name - java_programming_tutorial
		//Display name - java_prog.pdf
		//Add dependency - apache commons io
		
		String extension = FilenameUtils.getExtension(originalFileName);
		String fileName = FilenameUtils.removeExtension(originalFileName);
		
		if(fileName.length()>8)
		{
			fileName = fileName.substring(0, 7);
		}
		
		fileName = fileName + "." + extension;
		
		return fileName;
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


	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
		// TODO Auto-generated method stub
		
				
		FileInputStream io = new FileInputStream(fileDetails.getPath());
		
		//Convert stream to byte
		byte[] byteData = StreamUtils.copyToByteArray(io);
		
		return byteData;
	}


	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
		FileDetails fileDetails = fileRepository.findById(id).orElseThrow(() ->
		new ResourceNotFoundException("File not available"));
		
		return fileDetails;
	}


	@Override
	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		
		//Pagination 
		//Total 10 notes - 5 on one page - total 2 pages
		//Pageable pageable = PageRequest.of(3, 5);  //First page number and second page number and page number starts from 0 index
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		Page<Notes> pageNotes = notesRepository.findByCreatedByAndIsDeletedFalse(userId, pageable);
		
		List<NotesDto> notesDto = pageNotes.getContent()
	            .stream().map(n -> modelMapper.map(n, NotesDto.class)).toList();
		
		NotesResponse notes = NotesResponse.builder()
				.notes(notesDto)
				.pageNo(pageNotes.getNumber())
				.pageSize(pageNotes.getSize())
				.totalElements(pageNotes.getTotalElements())
				.totalPages(pageNotes.getTotalPages())
				.first(pageNotes.isFirst())
				.last(pageNotes.isLast())
				.build();
		
		return notes;
	}


	//Data delete from database and recycle bin
	@Override
	public void softDeleteNotes(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
		Notes notes = notesRepository.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException("Notes id invalid ! Not Found"));
		
		notes.setIsDeleted(true);
		
		notes.setDeletedOn(LocalDateTime.now());
		
		notesRepository.save(notes);
	}


	@Override
	public void restoreNotes(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
		Notes notes = notesRepository.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException("Notes id invalid ! Not Found"));
		
		notes.setIsDeleted(false);
		
		notes.setDeletedOn(null);
		
		notesRepository.save(notes);
	}


	//Data check in recycle bin which are deleted
	@Override
	public List<NotesDto> getUserRecycleBinNotes(Integer userId) {
		// TODO Auto-generated method stub
		
		List<Notes> recycleNotes = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
		
		List<NotesDto> notesDtoList = recycleNotes.stream().map(note -> modelMapper.map(note, NotesDto.class)).toList();
		
		return notesDtoList;
	}


	@Override
	public void hardDeleteNotes(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
		Notes notes = notesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes not found"));
		
		if(notes.getIsDeleted())   // if idDelete true
		{
			notesRepository.delete(notes);
		}
		else
		{
			throw new IllegalArgumentException("Sorry you can't hard delete directly");
		}
	}


	@Override
	public void emptyRecycleBin(Integer userId) {
		// TODO Auto-generated method stub
		
		List<Notes> emptyNotes = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
		
		if(!CollectionUtils.isEmpty(emptyNotes))
		{
			notesRepository.deleteAll(emptyNotes);
		}
		
	}

	
	
	
	
}
