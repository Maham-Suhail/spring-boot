package com.example.demo.StudentControllerTest;


import com.example.demo.StudentController.StudentController;

import com.example.demo.entity.Student;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StudentControllerTest {


    @InjectMocks
    StudentController controller;
    @LocalServerPort
    int randomServerPort;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new StudentController();
    }

    @Test
    void shouldListStudents() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/students";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        //Verify request succeed
        
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.hasBody());
    }

    @Test
    void shouldGetStudentById() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/students/{id}";
        try{
        Map<String, Integer> params = new HashMap<>();
        params.put("id", 1);
        Student getStudent = restTemplate.getForObject(baseUrl,Student.class,params);
        Assert.assertNotNull("Successfully get student by Id",getStudent);
    }
        catch (NoSuchElementException exception)
        {
            exception.printStackTrace();
        }
    }

    @Test
    void shouldAddStudent() {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/students";
        try{

            Student newStudent = new Student(4,"Ahmed","sheikh","as@mail.com");
            Student addStudent = restTemplate.postForObject(baseUrl,newStudent,Student.class);
            Assert.assertNotNull("New student added",addStudent);
        }
        catch (NoSuchElementException exception)
        {
            exception.printStackTrace();
        }
    }


    @Test
    void shouldDeleteStudentById() {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/students/{id}";
        try{
            Map<String, Integer> params = new HashMap<>();
            params.put("id", 4);
            restTemplate.delete(baseUrl,params);

        }
        catch (NoSuchElementException exception)
        {
            exception.printStackTrace();
        }
    }

    @Test
    void shouldUpdate() {

        RestTemplate restTemplate = new RestTemplate();
        final String baseUri = "http://localhost:" + randomServerPort + "/students/{id}";
        try {
            Map<String, Integer> params = new HashMap<>();
            params.put("id", 2);

            Student updatedStudent = new Student(2,"New", "name", "test@email.com");
            Student student = restTemplate.getForObject(baseUri,Student.class,params);
            Assert.assertEquals("student updated",student.getId(),updatedStudent.getId());
            restTemplate.put(baseUri,updatedStudent,params);

        }
        catch (NoSuchElementException exception)
        {
            exception.printStackTrace();
        }
    }
}
