package com.enotes.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.enotes.entity.Notes;
import com.enotes.repository.NotesRepository;

@Component
public class NotesSchedular {

	@Autowired
	private NotesRepository notesRepository;
	//int i=0;
	
	//@Scheduled(fixedRate = 1000)
	@Scheduled(cron = "0 0 0 * * ?")//for every day at 00.00
	//@Scheduled(cron = "0 0 0 ? * *")  //for every second
	public void deleteNotesSchedular()
	{
	
		//i++;
	//System.out.println("i:" + i);
		
		//Delete notes by scheduling before 7 days
		LocalDateTime cutoffDate = LocalDateTime.now().minusDays(7);
		
		List<Notes> deleteNotes = notesRepository.findAllByIsDeletedAndDeletedOnBefore(true, cutoffDate);
	
		notesRepository.deleteAll(deleteNotes);
	
	}
}
