package com.enotes.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;



public class GenericResponse {

	

	private HttpStatus responseStatus;
	
	private String status;     //success or fail
	
	private String message;    //saved successfully, register successfully
	
	private Object data;

	
	
	// 🔹 Private constructor (Builder will use this)
    private GenericResponse(Builder builder) {
        this.responseStatus = builder.responseStatus;
        this.status = builder.status;
        this.message = builder.message;
        this.data = builder.data;
    }

    // 🔹 Public no-arg constructor (optional)
    public GenericResponse() {}

    // 🔹 Static entry point (IMPORTANT)
    public static Builder builder() {
        return new Builder();
    }

    // ================= BUILDER =================
    public static class Builder {

        private HttpStatus responseStatus;
        private String status;
        private String message;
        private Object data;

        public Builder responseStatus(HttpStatus responseStatus) {
            this.responseStatus = responseStatus;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public GenericResponse build() {
            return new GenericResponse(this);
        }
    }
    
		    
	    public ResponseEntity<?> create()
	    {
	    	Map<String, Object> map = new LinkedHashMap<>();
	    	
	    	map.put("status", status);
	    	map.put("message", message);
	    	
	    	if(!ObjectUtils.isEmpty(data))
	    	{
	    		map.put("data", data);
	    	}
	    	
	    	return new ResponseEntity<>(map, responseStatus);
	    }




	
	
}
