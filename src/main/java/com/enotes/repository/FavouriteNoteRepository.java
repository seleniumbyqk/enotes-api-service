package com.enotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.FavouritNote;

public interface FavouriteNoteRepository extends JpaRepository<FavouritNote, Integer>{

	List<FavouritNote> findByUserId(Integer userId);

}
