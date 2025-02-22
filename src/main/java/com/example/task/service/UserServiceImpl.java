package com.example.task.service;

import com.example.task.exception.UserNotFoundException;
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
import org.modelmapper.ModelMapper;
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
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepo;

  private final ModelMapper mapper;

	/**
	 * Method to get all the users from the repository
	 * @return list of users
	 */
	public List<UserResponse> getAllUsers(){
		LOGGER.info("Getting users from repository ");
		List<User> list = userRepo.findAll();
		List<UserResponse> returnList = new ArrayList();
    list.forEach(
      user -> returnList.add( mapper.map(user, UserResponse.class)));
		return returnList;
	}

	/**
	 * Method to fetch a user from an id
	 * @param id the user id to fetch details
	 * @return The UserResponse details fetched
   */
	public UserResponse getUser(long id) {
    LOGGER.info("Finding user by id {}", id);
		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("user not found :: " + id));
    return mapper.map(user, UserResponse.class);
	}

	/**
	 * Method to create a new User
	 * @param userRequest the request details for user creation
	 * @return The user details created
	 */
	@Transactional
	public UserResponse createUser(@RequestBody @Valid UserRequest userRequest){
		LOGGER.info("Creating User ");
		User userCreated = userRepo.save(mapper.map(userRequest, User.class));
    return mapper.map(userCreated, UserResponse.class);
	}

	/**
	 * Method to check and update a user
	 * @param id the user id to fetch details
	 * @param userRequest the request details for user update
	 * @return The User details Updated
   */
	@Transactional
	public UserResponse updateUser(long id , UserRequest userRequest) {
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
            todos.add(mapper.map(todo, Todo.class) );
          }
          user.setTodos(todos);
            LOGGER.info("updating user by id {}", user);
          }
        updatedUser =  mapper.map(userRepo.save(user), UserResponse.class) ;
		return updatedUser;
	}

	/**
	 *  Method to delete a user
	 * @param id the user id to delete
   */
	@Transactional
	public void deleteUser(long id) {
    LOGGER.info("Deleting user by id {}", id);
		userRepo.findById(id)
				.orElseThrow(() -> new UserNotFoundException("user not found :: " + id));
			userRepo.deleteById(id);
  }

}
