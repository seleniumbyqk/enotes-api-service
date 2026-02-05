package com.enotes.dto;

public class FavouritNoteDto {

	private Integer id;

	private NotesDto note;

	private Integer userId;

	public FavouritNoteDto() {
		super();
	}

	public FavouritNoteDto(Integer id, NotesDto note, Integer userId) {
		super();
		this.id = id;
		this.note = note;
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public NotesDto getNote() {
		return note;
	}

	public void setNote(NotesDto note) {
		this.note = note;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "FavouritNoteDto [id=" + id + ", note=" + note + ", userId=" + userId + "]";
	}
	
	// Private constructor used by Builder
    private FavouritNoteDto(Builder builder) {
        this.id = builder.id;
        this.note = builder.note;
        this.userId = builder.userId;
    }
    
    // ✅ STATIC BUILDER METHOD (THIS WAS MISSING)
    public static Builder builder() {
        return new Builder();
    }
    
 // ---------- BUILDER ----------
    public static class Builder {

        private Integer id;
        private NotesDto note;
        private Integer userId;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder note(NotesDto note) {
            this.note = note;
            return this;
        }

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }
        
        public FavouritNoteDto build() {
            return new FavouritNoteDto(this);
        }
    }
	
}
