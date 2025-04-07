package com.springdemo.dataentry.service;
//This is service layer where the actual business logic lies

import com.springdemo.dataentry.model.User;
import com.springdemo.dataentry.repository.UserRepository;
import com.springdemo.dataentry.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.*;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Create Operation Method
    @Override
    public void saveUser(User user) {
        logger.trace("Entering saveUser method from the controller class to save the data in db");
        userRepository.save(user); // Save the user to the database
        logger.trace("User successfully saved to the database with usenname: {} and exits from the saveUser method.", user.getName());
    }

    //Read Operation Methods
    @Override
    public List<User> getAllUsers() {
        logger.trace("Entering getAllUsers(all) method from the controller class to get all the users in db");
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        logger.trace("Received the id from the controller class: {} ",id);
        Optional<User> user = userRepository.findById(id);

        logger.trace("Checking for a user with the id:{}",id);
        if (user.isPresent()) {
            logger.trace(" Checking from service class, User found successfully in the database and sending the information to controller class");
        } else {
            logger.trace("Checking from the service class ,User not found in the database");
        }
        return user;
    }

    //Update Operation Method Implementation
    @Override
    public void updateUser(Long id, User user) {
        logger.trace("Entering updateUser method in the service method with with no id & User Details:{}", user);
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            logger.trace("As User with the specified id is present in the database, updation takes place");
            User existingUser = existingUserOptional.get();
            logger.trace("User name is being updated now");
            existingUser.setName(user.getName());
            logger.trace("User age  is being updated now");
            existingUser.setAge(user.getAge());
            userRepository.save(existingUser); // Save the updated user
            logger.trace("User with ID {} updated successfully", id);
        }else {
            logger.trace("Entered into else statement as user is not found ,it is e");
            logger.warn("User with ID {} not found", id);
            throw new UserNotFoundException("User not found with id: " + id);
        }
        //logger.trace("Exiting updateUser method for user with ID:{} and User Details:{}", id, existingUserOptional);
        //The reason why we replaced User with existingUserOptional is that we are updating the id of existinguseroptional
        //with the User . So User has only name and age and it does not get id because it is not saved in db
        //we are just updating it . So inorder to get id in trace we use existing......../
    }

    //Delete Operation Method Implementation
    @Override
    public void deleteUser(Long id){
        logger.trace("Entering the deleteuser method in the service method and checking if the user is present or not");
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            logger.trace("As user with id:{} is present, it will be deleted next step",id);
            userRepository.deleteById(id);// Delete the user by ID
        } else {
            logger.trace("As user with id: {} is not found, we got into else statement",id);
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }
}