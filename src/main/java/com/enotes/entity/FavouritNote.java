package com.enotes.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class FavouritNote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Notes note;
	
	private Integer userId;

	public FavouritNote() {
		super();
	}

	public FavouritNote(Integer id, Notes note, Integer userId) {
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

	public Notes getNote() {
		return note;
	}

	public void setNote(Notes note) {
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
		return "FavouritNote [id=" + id + ", note=" + note + ", userId=" + userId + "]";
	}
	
	
	// Private constructor → only Builder can create object
    private FavouritNote(Builder builder) {
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
        private Notes note;
        private Integer userId;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder note(Notes note) {
            this.note = note;
            return this;
        }

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public FavouritNote build() {
            return new FavouritNote(this);
        }
    }

	
}
