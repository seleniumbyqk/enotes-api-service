package com.enotes.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Category extends BaseModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String description;
	

    private Boolean isActive;
	
	private Boolean isDeleted;

	public Category() {
		super();
	}


	public Category(Integer id, String name, String description, Boolean isActive, boolean isDeleted) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}


	public Boolean getIsDeleted() {
		return isDeleted;
	}


	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


	
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", description=" + description + ", isActive=" + isActive
				+ ", isDeleted=" + isDeleted + "]";
	}
	
	
	
	// Private constructor used by Builder
    private Category(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.isActive = builder.isActive;
        this.isDeleted =builder.isDeleted;
    }
    
    // ✅ STATIC BUILDER METHOD (THIS WAS MISSING)
    public static Builder builder() {
        return new Builder();
    }
    
    
    // ---------------- BUILDER ----------------
    public static class Builder {
    
    	private Integer id;
    	
    	private String name;
    	
    	private String description;
    	

        private Boolean isActive;
    	
    	private Boolean isDeleted;
    		
    		public Builder id(Integer id) {
                this.id = id;
                return this;
            }
    		
    		public Builder name(String name) {
                this.name = name;
                return this;
            }
    		
    		public Builder description(String description) {
                this.description = description;
                return this;
            }
    		
    		public Builder isActive(Boolean isActive) {
                this.isActive = isActive;
                return this;
            }
    		
    		public Builder isDeleted(Boolean isDeleted) {
                this.isDeleted = isDeleted;
                return this;
            }
    		
    		
    		
    		public Category build() {
                return new Category(this);
            }
    }
    
}
