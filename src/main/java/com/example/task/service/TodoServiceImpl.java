package com.example.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.task.mapper.SubTaskMapper;
import com.example.task.mapper.TodoMapper;
import com.example.task.model.request.SubTaskRequest;
import com.example.task.model.request.TodoRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.task.model.entities.SubTask;
import com.example.task.model.entities.Todo;
import com.example.task.exception.TodoNotFoundException;
import com.example.task.repo.SubtaskRepository;
import com.example.task.repo.TodoRepository;
import com.example.task.model.response.TodoResponse;


@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TodoServiceImpl.class);

	private final	TodoRepository todoRepo;

  private final	SubtaskRepository subtaskRepo;

  private final TodoMapper todoMapper;

  private final SubTaskMapper subTaskMapper;

  	/**
	 * Method to get all the todos from the repository
	 * @return list of Todos
	 */
	public List<TodoResponse> getAllTodos(){
		LOGGER.info("Getting todos from repository ");
		List<Todo> list = todoRepo.findAll();
		List<TodoResponse> returnList = new ArrayList();
    list.forEach(
      todo -> returnList.add( todoMapper.toTodoResponse(todo)));
		return returnList;
	}

	/**
	 * Method to fetch a todo from an id
	 * @param id the todo id to fetch details
	 * @return The TodoResponse details fetched
   */
	public TodoResponse getTodo(UUID id) {
    LOGGER.info("Finding todo by id {}", id);
		Todo todo = todoRepo.findById(id).orElseThrow(() -> new TodoNotFoundException("todo not found :: " + id));
    return todoMapper.toTodoResponse(todo);
	}

	/**
	 * Method to create a new Todo
	 * @param todoRequest the request details for todo creation
	 * @return The Todo details created
	 */
	@Transactional
	public TodoResponse createTodo(@RequestBody @Valid TodoRequest todoRequest){
		LOGGER.info("Creating todo ");
		Todo todoCreated = todoRepo.save(todoMapper.toTodoEntity(todoRequest));
    return todoMapper.toTodoResponse(todoCreated);
	}

	/**
	 * Method to check and update a todo
	 * @param id the todo id to fetch details
	 * @param todoRequest the request details for todo update
	 * @return The Todo details Updated
   */
	@Transactional
	public TodoResponse updateTodo(UUID id , TodoRequest todoRequest) {
    LOGGER.info("updating todo by id {}", id);
		Todo todo;
		TodoResponse updatedTodo = null ;
			todo = todoRepo.findById(id)
					.orElseThrow(() -> new TodoNotFoundException("todo not found :: " + id));
			 if(StringUtils.isNotEmpty(todoRequest.getName())) {
			  todo.setName(todoRequest.getName());
       }
			  if(StringUtils.isNotEmpty(todoRequest.getDescription())) {
			  todo.setDescription(todoRequest.getDescription());
        }
          if(CollectionUtils.isNotEmpty(todoRequest.getTasks())) {
            LOGGER.info("updating todo task by id {}", id);
          Set<SubTask> tasks = todo.getTasks();
          Set<SubTaskRequest> taskDtos = todoRequest.getTasks();
          for(SubTaskRequest task :taskDtos) {
             tasks.add(subTaskMapper.toSubTask(task) );
          }
          todo.setTasks(tasks);
            LOGGER.info("updating todo by id {}", todo);
          }
        updatedTodo =  todoMapper.toTodoResponse(todoRepo.save(todo)) ;
		return updatedTodo;
	}

	/**
	 *  Method to delete a todo
	 * @param id the todo id to delete
   */
	@Transactional
	public void deleteTodo(UUID id) {
    LOGGER.info("Deleting todo by id {}", id);
		todoRepo.findById(id)
				.orElseThrow(() -> new TodoNotFoundException("todo not found :: " + id));
			todoRepo.deleteById(id);
  }

	@Override
	public List<SubTask> getSubtask(UUID id, String name)  {
		Todo todo = todoRepo.findById(id)
				.orElseThrow(() -> new TodoNotFoundException("todo not found :: " + id));
    return subtaskRepo.findBySubtaskName(id,name);
	}
}
