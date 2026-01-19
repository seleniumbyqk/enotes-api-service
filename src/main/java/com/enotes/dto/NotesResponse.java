package com.enotes.dto;

import java.util.List;

public class NotesResponse {

	private List<NotesDto> notes;
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private Long totalElements;
	
	private Integer totalPages;
	
	private Boolean first;
	
	private Boolean last;

	public NotesResponse() {
		super();
	}

	public NotesResponse(List<NotesDto> notes, Integer pageNo, Integer pageSize, Long totalElements,
			Integer totalPages, Boolean first, Boolean last) {
		super();
		this.notes = notes;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.first = first;
		this.last = last;
	}

	public List<NotesDto> getNotes() {
		return notes;
	}

	public void setNotes(List<NotesDto> notes) {
		this.notes = notes;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	

	 public Boolean getFirst() {
	        return first;
	    }

	public void setFirst(Boolean first) {
		this.first = first;
	}

	 public Boolean getLast() {
	        return last;
	    }

	public void setLast(Boolean last) {
		this.last = last;
	}

	@Override
	public String toString() {
		return "NotesResponse [notes=" + notes + ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", totalElements="
				+ totalElements + ", totalPages=" + totalPages + ", isFirst=" + first + ", isLast=" + last + "]";
	}
	
	
	// Private constructor → only Builder can create object
    private NotesResponse(Builder builder) {
        this.notes = builder.notes;
        this.pageNo = builder.pageNo;
        this.pageSize = builder.pageSize;
        this.totalElements = builder.totalElements;
        this.totalPages = builder.totalPages;
        this.first = builder.first;
        this.last = builder.last;
    }
    
    // ✅ STATIC BUILDER METHOD (THIS WAS MISSING)
    public static Builder builder() {
        return new Builder();
    }
	
    // ---------- BUILDER ----------

    public static class Builder {

        private List<NotesDto> notes;
        private Integer pageNo;
        private Integer pageSize;
        private Long totalElements;
        private Integer totalPages;
        private Boolean first;
        private Boolean last;

        public Builder notes(List<NotesDto> notes) {
            this.notes = notes;
            return this;
        }

        public Builder pageNo(Integer pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public Builder pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder totalElements(Long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public Builder totalPages(Integer totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder first(Boolean first) {
            this.first = first;
            return this;
        }

        public Builder last(Boolean last) {
            this.last = last;
            return this;
        }

        public NotesResponse build() {
            return new NotesResponse(this);
        }

		
    }
    
}
