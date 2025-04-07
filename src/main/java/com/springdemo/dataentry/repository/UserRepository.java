//This is actually the repository layer : layer which deals with the database
package com.springdemo.dataentry.repository;
import com.springdemo.dataentry.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Additional query methods can be defined here if needed
}
