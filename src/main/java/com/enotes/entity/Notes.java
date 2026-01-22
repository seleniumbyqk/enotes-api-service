package com.enotes.entity;

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

	private LocalDateTime deletedOn;

	public Notes() {
		super();
	}

	public Notes(Integer id, String title, String description, Category category, FileDetails fileDetails,
			Boolean isDeleted, LocalDateTime deletedOn) {
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

	public LocalDateTime getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(LocalDateTime deletedOn) {
		this.deletedOn = deletedOn;
	}

	@Override
	public String toString() {
		return "Notes [id=" + id + ", title=" + title + ", description=" + description + ", category=" + category
				+ ", fileDetails=" + fileDetails + ", isDeleted=" + isDeleted + ", deletedOn=" + deletedOn + "]";
	}

	
	 // Private constructor for Builder
    private Notes(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.category = builder.category;
        this.fileDetails = builder.fileDetails;
        this.isDeleted = builder.isDeleted;
        this.deletedOn = builder.deletedOn;
    }
    
 // ✅ STATIC BUILDER METHOD (THIS WAS MISSING)
    public static Builder builder() {
        return new Builder();
    }
	
    
 // ---------------- BUILDER ----------------
    public static class Builder {

        private Integer id;
        private String title;
        private String description;
        private Category category;
        private FileDetails fileDetails;
        private Boolean isDeleted;
        private LocalDateTime deletedOn;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder fileDetails(FileDetails fileDetails) {
            this.fileDetails = fileDetails;
            return this;
        }

        public Builder isDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public Builder deletedOn(LocalDateTime deletedOn) {
            this.deletedOn = deletedOn;
            return this;
        }

        public Notes build() {
            return new Notes(this);
        }
    }
}
