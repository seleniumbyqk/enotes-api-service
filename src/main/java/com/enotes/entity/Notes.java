package com.enotes.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.enotes.dto.CategoryDto;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Notes extends BaseModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
	private String description;
	
	@ManyToOne
	 @JoinColumn(name = "category_id")
	private Category category;

	public Notes() {
		super();
	}

	public Notes(Integer id, String title, String description, Category category) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Notes [id=" + id + ", title=" + title + ", description=" + description + ", category=" + category + "]";
	}
	
	
}
