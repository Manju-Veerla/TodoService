package com.example.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.task.dto.SubTaskDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.internal.util.collections.ArrayHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.task.dto.TodoDto;
import com.example.task.entities.SubTask;
import com.example.task.entities.Todo;
import com.example.task.exception.TodoNotFoundException;
import com.example.task.repo.SubtaskRepository;
import com.example.task.repo.TodoRepository;
import com.example.task.response.TodoResponse;
import com.example.task.service.TodoService;



@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TodoServiceImpl.class);

	private final	TodoRepository todoRepo;

  private final	SubtaskRepository subtaskRepo;

  private final ModelMapper mapper;

	/**
	 * Method to get all the todos from the repository
	 * @return list of Todos
	 */
	public List<Todo> getAllTodos(){
		LOGGER.info("Getting todos from repository ");
		List<Todo> list = todoRepo.findAll();
		/*
		 * List<TodoResponse> returnList = new ArrayList(); list.forEach(todo ->
		 * returnList.add( mapper.map(todo, TodoResponse.class)));
		 */
		return list;
	}

	/**
	 * Method to fetch a todo from an id
	 * @param id the todo id to fetch details
	 * @return The Todo details fetched
	 * @throws TodoNotFoundException
	 */
	public TodoResponse getTodo(long id) throws TodoNotFoundException {
		LOGGER.info("Finding todo by id "+ id);
		Optional<Todo> todo = todoRepo.findById(id);
		if (!todo.isEmpty()) {
			TodoResponse todoResponse = mapper.map(todo, TodoResponse.class);
			return todoResponse;
		} else {
			LOGGER.error("Todo not found by id "+ id );
			throw new TodoNotFoundException("todo not found :: " + id);
		}
	}

	/**
	 * Method to create a new Todo
	 * @param todo the request details for todo creation
	 * @return The Todo details created
	 */
	@Transactional
	public TodoResponse createTodo(@RequestBody TodoDto todo){
		LOGGER.info("Creating todo ");
		Todo todoCreated = todoRepo.save(mapper.map(todo, Todo.class));
		TodoResponse todoResponse = mapper.map(todoCreated, TodoResponse.class);
		return todoResponse;
	}

	/**
	 * Method to check and update a todo
	 * @param id the todo id to fetch details
	 * @param todoDetails the request details for todo update
	 * @return The Todo details Updated
	 * @throws TodoNotFoundException
	 */
	@Transactional
	public TodoResponse updateTodo(long id , TodoDto todoDetails) throws TodoNotFoundException{
		LOGGER.info("updating todo by id "+ id);
		Todo todo;
		TodoResponse updatedTodo = null ;
			todo = todoRepo.findById(id)
					.orElseThrow(() -> new TodoNotFoundException("todo not found :: " + id));
			 if(StringUtils.isNotEmpty(todoDetails.getName())) {
			  todo.setName(todoDetails.getName());
       }
			  if(StringUtils.isNotEmpty(todoDetails.getDescription())) {
			  todo.setDescription(todoDetails.getDescription());
        }
          if(CollectionUtils.isNotEmpty(todoDetails.getTasks())) {
          LOGGER.info("updating todo task by id "+ id);
          Set<SubTask> tasks = todo.getTasks();
          Set<SubTaskDto> taskDtos = todoDetails.getTasks();
          for(SubTaskDto task :taskDtos) {
             tasks.add(mapper.map(task, SubTask.class) );
          }
          todo.setTasks(tasks);
          LOGGER.info("updating todo by id "+ todo);
          }
        updatedTodo =  mapper.map(todoRepo.save(todo), TodoResponse.class) ;


		return null;
	}

	/**
	 *  Method to delete a todo
	 * @param id the todo id to delete
	 * @throws TodoNotFoundException
	 */
	@Transactional
	public void deleteTodo(long id) throws TodoNotFoundException{
		LOGGER.info("Deleting todo by id "+ id);
		Todo todo = todoRepo.findById(id)
				.orElseThrow(() -> new TodoNotFoundException("todo not found :: " + id));
		if(null != todo) {
			todoRepo.deleteById(id);
		}


	}

	@Override
	public List<?> getSubtask(long id, String name) throws TodoNotFoundException {
		Todo todo = todoRepo.findById(id)
				.orElseThrow(() -> new TodoNotFoundException("todo not found :: " + id));
		 List subtasks = subtaskRepo.findBySubtaskName(id,name);

			/*
			 * Set<SubTask> subtasks = todo.getTasks(); List resultTasks = new ArrayList();
			 * for (SubTask subTask : subtasks) { if(subTask.getName().contains(name)) {
			 * resultTasks.add(subTask); } }
			 */
		return subtasks;
	}
}
