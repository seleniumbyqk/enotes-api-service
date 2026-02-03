package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.enotes.util.Constants.*;
import com.enotes.dto.TodoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Todo", description = "All the Todo OPeration APIs")
@RequestMapping("/api/v1/todo")
public interface TodoControllerEndpoint {

	// No need to mention @RequestBody, @PathVariable, @RequestParam in
	// implementation classes

	// @Requestbody - convert json to object
	@Operation(summary = "Save Todo Endpoint", tags = { "Todo" }, description = "User Save Todo")
	@PostMapping("/")
	// @PreAuthorize("hasRole('USER')")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception;

	@Operation(summary = "Get Todo By Id Endpoint", tags = { "Todo" }, description = "Get Todo By Id ")
	@GetMapping("/{id}")
	// @PreAuthorize("hasRole('USER')")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;

	@Operation(summary = "Get Todo By User Endpoint", tags = { "Todo" }, description = "Get Todo By User")
	@PostMapping("/list")
	// @PreAuthorize("hasRole('USER')")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoByUser() throws Exception;
}
