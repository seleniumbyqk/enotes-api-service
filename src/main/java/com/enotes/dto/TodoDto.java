package com.enotes.dto;

import java.util.Date;

public class TodoDto {

    private Integer id;
	
	private String title;
	
	private StatusDto status;
	
	private Integer createdBy;
	
	private Date createdOn;
	
	private Integer updatedBy;
	
	private Date updatedOn;
	
	
	//Created StatusDto to use enum
	public static class StatusDto{
		private Integer id;
		
		private String name;

		public StatusDto() {
			super();
		}

		public StatusDto(Integer id, String name) {
			super();
			this.id = id;
			this.name = name;
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
		
		
		 // Private constructor for Builder
	    private StatusDto(Builder builder) {
	        this.id = builder.id;
	        this.name = builder.name;
	    }
	    
	    // ✅ STATIC BUILDER METHOD (THIS WAS MISSING)
	    public static Builder builder() {
	        return new Builder();
	    }
	    
	 // ----------- BUILDER -----------
	    public static class Builder {

	        private Integer id;
	        private String name;

	        public Builder id(Integer id) {
	            this.id = id;
	            return this;
	        }

	        public Builder name(String name) {
	            this.name = name;
	            return this;
	        }

	        public StatusDto build() {
	            return new StatusDto(this);
	        }
	    }
		
	}

	public TodoDto() {
		super();
	}

	public TodoDto(Integer id, String title, StatusDto status, Integer createdBy, Date createdOn, Integer updatedBy,
			Date updatedOn) {
		super();
		this.id = id;
		this.title = title;
		this.status = status;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public StatusDto getStatus() {
		return status;
	}

	public void setStatus(StatusDto status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "TodoDto [id=" + id + ", title=" + title + ", status=" + status + ", createdBy=" + createdBy
				+ ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + "]";
	}
	
	
	// Private constructor used by Builder
    private TodoDto(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.status = builder.status;
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
        private String title;
        private StatusDto status;
        private Integer createdBy;
        private Date createdOn;
        private Integer updatedBy;
        private Date updatedOn;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(StatusDto status) {
            this.status = status;
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

        public TodoDto build() {
            return new TodoDto(this);
        }
    }
	
}
