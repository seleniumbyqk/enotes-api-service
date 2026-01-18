package com.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enotes.entity.Notes;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Integer>{

}
