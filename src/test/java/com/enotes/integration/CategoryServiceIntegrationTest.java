package com.enotes.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.exception.ExistDataException;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc

public class CategoryServiceIntegrationTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	//@Autowired
	//private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
    // Inject actual service bean
    @Autowired
    private CategoryService categoryService;
	
		
	private CategoryDto categoryDto = null;
	private Category category = null;
	private List<Category> categories = new ArrayList<>();
	private List<CategoryDto> categoriesDtos = new ArrayList<>();
	
	//private final String token = null;
	
	@BeforeEach
	public void initialize()
	{
		mockLoggedInUser();
		
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
	
	private void mockLoggedInUser() {

	    UsernamePasswordAuthenticationToken authentication =
	            new UsernamePasswordAuthenticationToken(
	                    "test-user",              // principal
	                    null,
	                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
	            );

	    SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
    // =====================================================
    // saveCategory() TESTS
    // =====================================================

    /**
     * POSITIVE:
     * Should save category successfully
     */
    @Test
    void testSaveCategory_success() {

        Boolean result = categoryService.saveCategory(categoryDto);

        assertTrue(result, "Category should be saved successfully");
    }

    /**
     * NEGATIVE:
     * Should throw exception when category already exists
     */
    @Test
    void testSaveCategory_duplicateName() {

        // First save (success)
        categoryService.saveCategory(categoryDto);

        // Second save with same name (failure)
        assertThrows(ExistDataException.class, () -> {
            categoryService.saveCategory(categoryDto);
        });
    }


    // =====================================================
    // getAllCategory() TESTS
    // =====================================================

    /**
     * POSITIVE:
     * Should return all non-deleted categories
     */
    @Test
    void testGetAllCategory_success() {

        // Insert data
        categoryService.saveCategory(categoryDto);

        List<CategoryDto> categories = categoryService.getAllCategory();

        assertFalse(categories.isEmpty(), "Category list should not be empty");
    }

    /**
     * NEGATIVE:
     * Should return empty list if no data exists
     */
    @Test
    void testGetAllCategory_empty() {

        List<CategoryDto> categories = categoryService.getAllCategory();

        assertNotNull(categories);
        // Empty list is VALID behavior
    }

    // =====================================================
    // getActiveCategory() TESTS
    // =====================================================

    /**
     * POSITIVE:
     * Should return only active & non-deleted categories
     */
    @Test
    void testGetActiveCategory_success() {

        categoryService.saveCategory(categoryDto);

        List<CategoryResponse> activeCategories =
                categoryService.getActiveCategory();

        assertFalse(activeCategories.isEmpty());
    }

    /**
     * NEGATIVE:
     * Should return empty list when no active category exists
     */
    @Test
    void testGetActiveCategory_empty() {

        List<CategoryResponse> activeCategories =
                categoryService.getActiveCategory();

        assertNotNull(activeCategories);
    }

    // =====================================================
    // getCategoryById() TESTS
    // =====================================================

    /**
     * POSITIVE:
     * Should return category by ID
     */
    @Test
    void testGetCategoryById_success() throws Exception {

        // Save category
        categoryService.saveCategory(categoryDto);

        // Fetch all categories to get ID
        List<CategoryDto> categories = categoryService.getAllCategory();
        Integer id = categories.get(0).getId();

        CategoryDto result = categoryService.getCategoryById(id);

        assertNotNull(result);
        assertEquals(result.getName(), result.getName().toUpperCase(),
                "Name should be returned in uppercase");
    }

    /**
     * NEGATIVE:
     * Should throw exception when ID not found
     */
    @Test
    void testGetCategoryById_notFound() {

        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.getCategoryById(999999);
        });
    }

    // =====================================================
    // deleteCategoryDetailsById() TESTS
    // =====================================================

    /**
     * POSITIVE:
     * Should delete category (soft delete)
     */
    @Test
    void testDeleteCategory_success() {

        categoryService.saveCategory(categoryDto);

        Integer id = categoryService.getAllCategory().get(0).getId();

        boolean deleted = categoryService.deleteCategoryDetailsById(id);

        assertTrue(deleted);

        // After delete, fetching should fail
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.getCategoryById(id);
        });
    }

    /**
     * NEGATIVE:
     * Should return false for invalid ID
     */
    @Test
    void testDeleteCategory_invalidId() {

        boolean deleted = categoryService.deleteCategoryDetailsById(999999);

        assertFalse(deleted);
    }

}
