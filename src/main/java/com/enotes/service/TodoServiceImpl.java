package com.enotes.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.TodoDto;
import com.enotes.dto.TodoDto.StatusDto;
import com.enotes.entity.Todo;
import com.enotes.enums.TodoStatus;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.repository.TodoRepository;
import com.enotes.util.CommonUtil;
import com.enotes.util.Validation;

@Service
public class TodoServiceImpl implements TodoService{

	/*
	//field injection
	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Validation validation;
	*/
	
	//constructor injection
	private final TodoRepository todoRepository;
	private final ModelMapper modelMapper;
	private final Validation validation;
	
	public TodoServiceImpl(TodoRepository todoRepository, ModelMapper modelMapper, Validation validation)
	{
		this.todoRepository = todoRepository;
		this.modelMapper = modelMapper;
		this.validation = validation;
	}
	
	
	@Override
	public Boolean saveTodo(TodoDto todoDto) throws Exception {
		// TODO Auto-generated method stub
		
		//Validate todo status;
		validation.todoValidation(todoDto);
				
		Todo todo = modelMapper.map(todoDto, Todo.class);
		
		todo.setStatusId(todoDto.getStatus().getId());
		
		Todo saveTodo = todoRepository.save(todo);
		
		if(!ObjectUtils.isEmpty(saveTodo))
		{
			return true;
		}
		
		return false;
	}

	@Override
	public TodoDto getTodoById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
		Todo todo = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo id not found"));
		
		TodoDto todoDto = modelMapper.map(todo, TodoDto.class);
		
		setStatus(todoDto, todo);
		
		return todoDto;
	}

	private void setStatus(TodoDto todoDto, Todo todo) {
		// TODO Auto-generated method stub
		
		for(TodoStatus st:TodoStatus.values())
		{
			if(st.getId().equals(todo.getStatusId()))
			{
				StatusDto statusDto = StatusDto.builder()
						.id(st.getId())
						.name(st.getName())
						.build();
				
				todoDto.setStatus(statusDto);
			}
		}
		
	}

	@Override
	public List<TodoDto> getTodoByUser() {
		// TODO Auto-generated method stub
		
		
		//Integer userId = 2;
		Integer userId = CommonUtil.getLoggedInUser().getId();
		
		List<Todo> todos = todoRepository.findByCreatedBy(userId);
		
		//When list is there use stream
		List<TodoDto> todoList = todos.stream().map(td -> modelMapper.map(td, TodoDto.class)).toList();
		
		return todoList;
	}

	
}
