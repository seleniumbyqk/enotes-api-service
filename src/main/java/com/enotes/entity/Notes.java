package com.enotes.entity;

import java.util.Date;

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
public class Notes extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String title;

	private String description;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	private FileDetails fileDetails;

	private Boolean isDeleted;

	private Date deletedOn;

	public Notes() {
		super();
	}

	public Notes(Integer id, String title, String description, Category category, FileDetails fileDetails,
			Boolean isDeleted, Date deletedOn) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.category = category;
		this.fileDetails = fileDetails;
		this.isDeleted = isDeleted;
		this.deletedOn = deletedOn;
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

	public FileDetails getFileDetails() {
		return fileDetails;
	}

	public void setFileDetails(FileDetails fileDetails) {
		this.fileDetails = fileDetails;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(Date deletedOn) {
		this.deletedOn = deletedOn;
	}

	@Override
	public String toString() {
		return "Notes [id=" + id + ", title=" + title + ", description=" + description + ", category=" + category
				+ ", fileDetails=" + fileDetails + ", isDeleted=" + isDeleted + ", deletedOn=" + deletedOn + "]";
	}

	
}
