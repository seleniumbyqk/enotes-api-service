package com.enotes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.TodoDto;
import com.enotes.endpoint.TodoControllerEndpoint;
import com.enotes.service.TodoService;
import com.enotes.service.TodoServiceImpl;
import com.enotes.util.CommonUtil;

@RestController
//@RequestMapping("/api/v1/todo")
public class TodoController implements TodoControllerEndpoint{

   

	//@Autowired
	//private TodoService todoService;
	
	 private final TodoServiceImpl todoServiceImpl;
	private final TodoService todoService;
	
	public TodoController(TodoServiceImpl todoServiceImpl, TodoService todoService) 
	{
		this.todoServiceImpl = todoServiceImpl;
		this.todoService = todoService;
	}
	
	
	/*
    TodoController(TodoServiceImpl todoServiceImpl) {
        this.todoServiceImpl = todoServiceImpl;
    }
	*/
	
	//@Requestbody - convert json to object
	//@PostMapping("/")
	//@PreAuthorize("hasRole('USER')")
    @Override
	public ResponseEntity<?> saveTodo(TodoDto todo) throws Exception
	{
		Boolean saveTodo = todoService.saveTodo(todo);
		
		if(saveTodo)
		{
			return CommonUtil.createBuildResponseMessage("Todo saved success", HttpStatus.CREATED);
		}
		else
		{
			return CommonUtil.createErrorResponseMessage("Todo not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//@GetMapping("/{id}")
	//@PreAuthorize("hasRole('USER')")
    @Override
	public ResponseEntity<?> getTodoById(Integer id) throws Exception
	{
		TodoDto todoById = todoService.getTodoById(id);
		
		return CommonUtil.createErrorResponse(todoById, HttpStatus.OK);
	}
	
	
	//@PostMapping("/list")
	//@PreAuthorize("hasRole('USER')")
    @Override
	public ResponseEntity<?> getTodoByUser() throws Exception
	{
		List<TodoDto> todoList = todoService.getTodoByUser();
		
		if(CollectionUtils.isEmpty(todoList))
		{
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtil.createBuildResponse(todoList, HttpStatus.OK);
	}
}
