package com.enotes.dto;



public class EmailRequest {

	private String to;
	
	private String subject;
	
	private String title;
	
	private String message;

	public EmailRequest() {
		super();
	}

	public EmailRequest(String to, String subject, String title, String message) {
		super();
		this.to = to;
		this.subject = subject;
		this.title = title;
		this.message = message;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "EmailRequest [to=" + to + ", subject=" + subject + ", title=" + title + ", message=" + message + "]";
	}
	
	
	 // Private constructor used by Builder
    private EmailRequest(Builder builder) {
        this.to = builder.to;
        this.subject = builder.subject;
        this.title = builder.title;
        this.message = builder.message;
    }
    
    // ✅ STATIC BUILDER METHOD (THIS WAS MISSING)
    public static Builder builder() {
        return new Builder();
    }
    
    
 // ---------------- BUILDER ----------------
    public static class Builder {

        private String to;
        private String subject;
        private String title;
        private String message;

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public EmailRequest build() {
            return new EmailRequest(this);
        }
    }
	
	
}
