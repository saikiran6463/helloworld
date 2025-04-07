package com.springdemo.dataentry.service;

import com.springdemo.dataentry.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
   void saveUser(User user);//Method to save user

   //Read Operation Methods
   List<User> getAllUsers();

   Optional<User> getUserById(Long id);

   //Update Methods
   void updateUser(Long id,User user);

   //Delete Method
   void deleteUser(Long id);
}
