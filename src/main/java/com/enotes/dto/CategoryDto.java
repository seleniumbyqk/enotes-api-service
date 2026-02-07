package com.enotes.dto;

import java.util.Date;

public class CategoryDto {

    private Integer id;
	
    //These are predefined validations
    //@NotBlank
    //@Min(value = 10, message = "Minimum 10 characters")
    //@Max(value = 100, message = "Maximum 100 characters")
	private String name;
	
    //@NotBlank
    //@Min(value = 10, message = "Minimum 10 characters")
    //@Max(value = 100, message = "Maximum 100 characters")
	private String description;
	
    //@NotNull
    private Boolean isActive;
    
    private Boolean isDeleted;
		
	private Integer createdBy;
	
	private Date createdOn;
	
	private Integer updatedBy;
	
	private Date updatedOn;

	public CategoryDto() {
		super();
	}

	

	public CategoryDto(Integer id, String name, String description, Boolean isActive, Boolean isDeleted,
			Integer createdBy, Date createdOn, Integer updatedBy, Date updatedOn) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
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



	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}



	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}



	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
	 // Private constructor used by Builder
    private CategoryDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.isActive = builder.isActive;
        this.isDeleted = builder.isDeleted;
        this.createdBy = builder.createdBy;
        this.createdOn = builder.createdOn;
        this.updatedBy = builder.updatedBy;
        this.updatedOn = builder.updatedOn;
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
    			
    		private Integer createdBy;
    		
    		private Date createdOn;
    		
    		private Integer updatedBy;
    		
    		private Date updatedOn;
    		
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
    		
    		public Builder createdBy(Integer createdBy) {
                this.createdBy = createdBy;
                return this;
            }
    		
    		public Builder createdOn(Date createdOn) {
                this.createdOn = createdOn;
                return this;
            }
    		
    		public Builder updatedBy(Integer updatedBy) {
                this.updatedBy = updatedBy;
                return this;
            }
    		
    		public Builder updatedOn(Date updatedOn) {
                this.updatedOn = updatedOn;
                return this;
            }
    		
    		public CategoryDto build() {
                return new CategoryDto(this);
            }
    }
    
	
}
