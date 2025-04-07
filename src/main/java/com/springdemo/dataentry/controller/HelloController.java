package com.springdemo.dataentry.controller;

import com.springdemo.dataentry.exception.UserNotFoundException;
import com.springdemo.dataentry.model.AppUser;
import com.springdemo.dataentry.model.User;
import com.springdemo.dataentry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.slf4j.*;


import java.util.List;
import java.util.Optional;

// Annotations for CORS policy saying that react url will communicate with spring url
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users") // Base URL for the controller
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    /*
    //Method 1
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World! This is Sai Kiran Reddy Peddola learning Spring Boot for the first time";
    }
    //Method 2
    @GetMapping("/bye")
    public String sayBye(){
        return "Bye World! Bye World";
    }

    //Method 3
    @GetMapping("/hello/{name}")
    public String greetUser(@PathVariable String name){
        return "Hello, " +name+ "! This is Sai Kiran Reddy Peddola";
    }

    //Method 4
    @GetMapping("/greet")
    public String greetUserWithQuery(@RequestParam(defaultValue = "World") String name){
        return "Hello " + name +"!";
    }

    //Method 5
    @PostMapping("/create-user")
    public String createUser(@RequestBody AppUser user) {
        return "User created: " + user.getName() + ", Age: " + user.getAge();
    }
*/
    //Method 4[DI using field based]
    /*@Autowired
    private UserService userService;//Injects the UserService Implementation

    @GetMapping("/hello/{name}")
    public String greetUser(@PathVariable String name){
        return userService.greetUser(name);
    }*/


    //Method 5 which is [DI through Constructor based]
    private final UserService userService;

    //Constructor based DI
    @Autowired
    public HelloController(UserService userService) {
        this.userService = userService;
    }
   /* @GetMapping("/hello/{name}")
    public String greetUser(@PathVariable String name) {
        return userService.greetUser(name);
    }*/

    //Create Operation of CRUD
    //POST request to create a new user

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        logger.info("Received request to create a new user: {}", user);
        userService.saveUser(user);
        logger.info("User successfully saved with name: {}", user);
        return ResponseEntity.ok("User created Successfully");
    }

    //Read Operation of CRUD
    // GET request to retrieve all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Received request to get all users and calling getAllUsers() method in service class");
        List<User> users = userService.getAllUsers();
        logger.info("Users found from the service methods are : {}", users);
        return ResponseEntity.ok(users); // Returns the list of users as JSON
    }

    // GET request to retrieve a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        logger.info("Received request and called the the service method");
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()) {
            logger.info("Controller class informing that User found from the service method,");
            return ResponseEntity.ok(user.get());
        } else {
            String message = "User with ID " + id + " not found";
            logger.warn("Controller class informing that ,User not found from the service method");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    // PUT mapping inorder to update operation
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        logger.info("Received id and sending it to the service method");
        try{
            userService.updateUser(id, user);
            logger.info("Controller class informing that User found from the service method and updated with ID:{},user:{}", id, user);
            return ResponseEntity.ok("User updated successfully");
        } catch (UserNotFoundException e){
            logger.error("User not found from the service method");
            //System.exit(1);//This is the example of FATAL error where the system exits.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    //DELETE request to delete a user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        logger.info("Received id and sending it to the service method:{}",id);
        try {
            userService.deleteUser(id);
            logger.info("User with id: {} is deleted successfully by the service method",id);
            return ResponseEntity.ok("User deleted successfully");
        }catch (UserNotFoundException e){
            logger.error("User not found from the service method, so unable to delete it with id: {}",id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
