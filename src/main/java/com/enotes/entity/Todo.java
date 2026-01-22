package com.enotes.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Todo extends BaseModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
	
	//Modelmapper not working because return type is different in todo and todoDto so used @Column
	@Column(name = "status")
	private Integer statusId;

	public Todo() {
		super();
	}

	public Todo(Integer id, String title, Integer statusId) {
		super();
		this.id = id;
		this.title = title;
		this.statusId = statusId;
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

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", title=" + title + ", status=" + statusId + "]";
	}
	
	
	 // Private constructor used by Builder
    private Todo(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.statusId = builder.statusId;
    }
    
    // ✅ STATIC BUILDER METHOD (THIS WAS MISSING)
    public static Builder builder() {
        return new Builder();
    }
    
    // ---------------- BUILDER ----------------
    public static class Builder {

        private Integer id;
        private String title;
        private Integer statusId;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder statusId(Integer statusId) {
            this.statusId = statusId;
            return this;
        }

        public Todo build() {
            return new Todo(this);
        }
    }

	
}
