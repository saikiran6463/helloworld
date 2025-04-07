package com.springdemo.dataentry.model;

import jakarta.persistence.*;

@Entity // Indicates that this class is a JPA entity
@Table(name = "users") // Specifies the table name in the database
public class User {
    @Id // Specifies the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates the ID
    private Long id;

    private String name; // Field for the user's name

    private int age; // Field for the user's age

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //Custom toString method is written so that SLF4J gets the actual user object instead of obj reference
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
    }

}
