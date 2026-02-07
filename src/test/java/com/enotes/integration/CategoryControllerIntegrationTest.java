package com.enotes.integration;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.LoginRequest;
import com.enotes.entity.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;




@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	//@Autowired
	//private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	 
	
		
	private CategoryDto categoryDto = null;
	private Category category = null;
	private List<Category> categories = new ArrayList<>();
	private List<CategoryDto> categoriesDtos = new ArrayList<>();
	
	//private final String token = null;
	
	@BeforeEach
	public void initialize()
	{
		 categoryDto = CategoryDto.builder()
				    .id(null)
					.name("Java Language Notes " + System.currentTimeMillis())
					.description("This is java language 1")
					.isActive(true)
					.isDeleted(false)
					.build();
		 
		 category = Category.builder()
				 .id(null)
				 .name("Java Notes " + + System.currentTimeMillis() )
				 .description("Java Notes 1")
				 .isActive(true)
				 .isDeleted(false)
				 .build();
		 
		 categories.add(category);
		 categoriesDtos.add(categoryDto);
		 
		 //category = modelMapper.map(categoryDto, Category.class);
	}
	
	// ------------------ SAVE CATEGORY ------------------
	
	@Test
	public void testSaveCategory_success_ADMIN() throws JsonProcessingException, Exception
	{
		
		String token = generateToken("bsm.mat2022@gmail.com", "Password@12345");
		
		mockMvc.perform(post("/api/v1/category/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryDto))
				.header("Authorization", token)
				)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.message").value("Saved success"))
		.andExpect(jsonPath("$.status").value("success"));
	}
	
	 @Test
	 public void saveCategory_validationFailure() throws Exception {

	        String token = generateToken("bsm.mat2022@gmail.com", "Password@12345");

	        // Invalid payload (name is null → @Valid fails)
	        categoryDto.setName(null);

	        mockMvc.perform(post("/api/v1/category/save")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(categoryDto))
	                .header("Authorization", token))
	                .andExpect(status().isBadRequest());
	    }
	 

	    @Test
	    public void testSaveCategory_unauthorized() throws Exception {

	        mockMvc.perform(post("/api/v1/category/save")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(categoryDto)))
	                .andExpect(status().isUnauthorized());
	    }
	
	    // ------------------ TOKEN GENERATION ------------------
	    
	public String generateToken(String email, String password) throws JsonProcessingException, Exception
	{
		LoginRequest login = new LoginRequest();
		login.setEmail(email);
		login.setPassword(password);
		
		String response = mockMvc.perform(post("/api/v1/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(login))
				
				)
		.andExpect(status().isOk())
		.andReturn()
		.getResponse()
		.getContentAsString();
		
		 JsonNode root = objectMapper.readTree(response);
		
		String token = root.path("data").path("token").asText();
		
		return "Bearer " + token;
	}
	
	// ------------------ GET ALL CATEGORY ------------------

    @Test
    void testGetAllCategory_success_ADMIN() throws Exception {

        String token = generateToken("bsm.mat2022@gmail.com", "Password@12345");

        mockMvc.perform(get("/api/v1/category/")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
    }
    
    
 // ------------------ GET ACTIVE CATEGORY ------------------

    @Test
    void testGetActiveCategory_success_USER() throws Exception {

        String token = generateToken("bsm.mat2022@gmail.com", "Password@12345");

        mockMvc.perform(get("/api/v1/category/active")
                .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActiveCategory_unauthorized() throws Exception {

        mockMvc.perform(get("/api/v1/category/active"))
                .andExpect(status().isUnauthorized());
    }
    
    
    // ------------------ GET CATEGORY BY ID ------------------

    @Test
    void testGetCategoryById_success() throws Exception {

        String token = generateToken("bsm.mat2022@gmail.com", "Password@12345");

        mockMvc.perform(get("/api/v1/category/{id}", 15)
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    void testGetCategoryById_notFound() throws Exception {

        String token = generateToken("bsm.mat2022@gmail.com", "Password@12345");

        mockMvc.perform(get("/api/v1/category/{id}", 99999)
                .header("Authorization", token))
                .andExpect(status().isNotFound());
    }

    // ------------------ DELETE CATEGORY ------------------

    @Test
    void testDeleteCategory_success() throws Exception {

        String token = generateToken("bsm.mat2022@gmail.com", "Password@12345");

        mockMvc.perform(delete("/api/v1/category/{id}", 1)
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Category deleted success"));
    }

    @Test
    void testDeleteCategory_invalidId() throws Exception {

        String token = generateToken("bsm.mat2022@gmail.com", "Password@12345");

        mockMvc.perform(delete("/api/v1/category/{id}", 99999)
                .header("Authorization", token))
                .andExpect(status().isInternalServerError());
    }


}
