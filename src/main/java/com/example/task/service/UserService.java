package com.example.task.service;

import com.example.task.exception.UserNotFoundException;
import com.example.task.mapper.TodoMapper;
import com.example.task.mapper.UserMapper;
import com.example.task.model.entities.Todo;
import com.example.task.model.entities.User;
import com.example.task.model.request.TodoRequest;
import com.example.task.model.request.UserRequest;
import com.example.task.model.response.UserResponse;
import com.example.task.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepo;

  private final UserMapper userMapper;

  private final TodoMapper todoMapper;


	/**
	 * Method to get all the users from the repository
	 * @return list of users
	 */
	public List<UserResponse> getAllUsers(){
		LOGGER.info("Getting users from repository ");
		List<User> list = userRepo.findAll();
		List<UserResponse> returnList = new ArrayList();
    list.forEach(
      user -> returnList.add(userMapper.toUserResponse(user)));
        //mapper.map(user, UserResponse.class)));
		return returnList;
	}

	/**
	 * Method to fetch a user from an id
	 * @param id the user id to fetch details
	 * @return The UserResponse details fetched
   */
	public UserResponse getUser(Integer id) {
    LOGGER.info("Finding user by id {}", id);
		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("user not found :: " + id));
    return userMapper.toUserResponse(user);
	}

	/**
	 * Method to create a new User
	 * @param userRequest the request details for user creation
	 * @return The user details created
	 */
	@Transactional
	public UserResponse createUser(@RequestBody @Valid UserRequest userRequest){
		LOGGER.info("Creating User ");
		User userCreated = userRepo.save(userMapper.toUser(userRequest));
    return userMapper.toUserResponse(userCreated);
	}

	/**
	 * Method to check and update a user
	 * @param id the user id to fetch details
	 * @param userRequest the request details for user update
	 * @return The User details Updated
   */
	@Transactional
	public UserResponse updateUser(Integer id , UserRequest userRequest) {
    LOGGER.info("updating user by id {}", id);
		User user;
		UserResponse updatedUser = null ;
			user = userRepo.findById(id)
					.orElseThrow(() -> new UserNotFoundException("user not found :: " + id));
			 if(StringUtils.isNotEmpty(userRequest.getName())) {
			  user.setUserName(userRequest.getName());
       }
			  if(StringUtils.isNotEmpty(userRequest.getEmail())) {
			  user.setEmail(userRequest.getEmail());
        }
          if(CollectionUtils.isNotEmpty(userRequest.getTodos())) {
            LOGGER.info("updating user todo by id {}", id);
          Set<Todo> todos = user.getTodos();
          Set<TodoRequest> todoRequests = userRequest.getTodos();
          for(TodoRequest todo :todoRequests) {
            todos.add(todoMapper.toTodoEntity(todo) );
          }
          user.setTodos(todos);
            LOGGER.info("updating user by id {}", user);
          }
        updatedUser =  userMapper.toUserResponse(userRepo.save(user)) ;
		return updatedUser;
	}

	/**
	 *  Method to delete a user
	 * @param id the user id to delete
   */
	@Transactional
	public void deleteUser(Integer id) {
    LOGGER.info("Deleting user by id {}", id);
		userRepo.findById(id)
				.orElseThrow(() -> new UserNotFoundException("user not found :: " + id));
			userRepo.deleteById(id);
  }

}
