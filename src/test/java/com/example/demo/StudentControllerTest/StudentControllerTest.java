package com.example.demo.StudentControllerTest;


import com.example.demo.StudentController.StudentController;

import com.example.demo.StudentRepository.CourseRepository;
import com.example.demo.StudentRepository.StudentRepository;
import com.example.demo.StudentService.StudentService;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.demo.StudentControllerTest.TestUtil.convertObjectToJsonBytes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import javax.persistence.EntityManager;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StudentControllerTest {

    @Autowired
    StudentController controller;
    @Autowired
    StudentRepository repository;
    @Autowired
    StudentService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    EntityManager entityManager;
    @Autowired
    CourseRepository courseRepository;

    private Student student;
    private Course course;

    public static Student createEntity() {
        Student student = new Student(5, "faria", "ali", "fariya@mail.com");
        return student;
    }

    public static Course createCourseEntity(){
        Course course = new Course(1,"calculas",3);
        return course;
    }

    @BeforeEach
    public void setUp() {
        student = createEntity();
        System.out.print("course Entity created");
        course = createCourseEntity();
    }
    @AfterEach
    public void removeData() {
        repository.delete(student);
        courseRepository.delete(course);
    }
    @Test
    void shouldListStudents() throws Exception {
        //initializing database
        repository.saveAndFlush(student);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/students")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(student.getFirstName())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(student.getLastName())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(student.getEmail())));


    }

    @Test
    void shouldGetStudentById() throws Exception {
        //initializing database
        repository.saveAndFlush(student);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/students/{id}",student.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.firstName").value(student.getFirstName()))
                .andExpect(jsonPath("$.lastName").value((student.getLastName())))
                .andExpect(jsonPath("$.email").value((student.getEmail())));


    }

    @Test
    void shouldAddStudent() throws Exception {

        int databaseSizeBeforeAdd = repository.findAll().size();

        repository.saveAndFlush(student);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(student)))
                .andExpect(status().isOk());
        List<Student> students = repository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeAdd+1);

    }


    @Test
    void shouldDeleteStudentById() throws Exception {
        // initialize database
        repository.saveAndFlush(student);
        //database size before delete
        int databaseSizeBeforeDelete = repository.findAll().size();

        //delete a student
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/students/{id}",student.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<Student> list = repository.findAll();
        assertThat(list).hasSize(databaseSizeBeforeDelete-1);

    }

    @Test
    void shouldUpdate() throws Exception {
        //initialize database
        repository.saveAndFlush(student);
        // database size
        int databaseSizeBeforeUpdate = repository.findAll().size();
        //update the object
        Student updatedStudent = repository.findById(student.getId()).get();
        updatedStudent.setId(5);
        updatedStudent.setFirstName("Fariha");
        updatedStudent.setLastName("ali");
        updatedStudent.setEmail("Fariha@mail.com");
        repository.saveAndFlush(updatedStudent);
        mockMvc.perform(put("/students/{id}",updatedStudent.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(updatedStudent)))
                .andExpect(status().isOk());

        List<Student> list = repository.findAll();
        assertThat(list).hasSize(databaseSizeBeforeUpdate);

    }

    @Test
    void shouldfindAllCourses() throws Exception {

        courseRepository.saveAndFlush(course);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/students/courses")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(course.getName())))
                .andExpect(jsonPath("$.[*].creditHour").value(hasItem(course.getCreditHour())));

    }

    @Test
    void shouldAddCourse() throws Exception {

        int databaseSizeBeforeAdd = courseRepository.findAll().size();

        courseRepository.saveAndFlush(course);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/students/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(course)))
                .andExpect(status().isOk());

        List<Course> courses = courseRepository.findAll();
        assertThat(courses).hasSize(databaseSizeBeforeAdd+1);
        System.out.print(courseRepository.findAll());
    }

    @Test
    void shouldGetCoursebyId() throws Exception {

        courseRepository.saveAndFlush(course);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/students/courses/{id}",course.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(course.getId()))
                .andExpect(jsonPath("$.name").value(course.getName()))
                .andExpect(jsonPath("$.creditHour").value(course.getCreditHour()));
    }

    @Test
    void shouldDeleteCourse() throws Exception {

        courseRepository.saveAndFlush(course);
        int databaseSizeBeforeDelete = courseRepository.findAll().size();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/students/courses/{id}",course.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<Course> courses = courseRepository.findAll();
        assertThat(courses).hasSize(databaseSizeBeforeDelete-1);
    }

    @Test
    void shouldUpdateCourse() throws Exception {

        courseRepository.saveAndFlush(course);

        int databaseSizebeforeupdate = courseRepository.findAll().size();

        Course updatedCourse = courseRepository.findById(course.getId()).get();
        updatedCourse.setId(1);
        updatedCourse.setName("applied physics");
        updatedCourse.setCreditHour(3);

        courseRepository.saveAndFlush(updatedCourse);

        mockMvc.perform(put("/students/courses/{id}",updatedCourse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(updatedCourse)))
                        .andExpect(status().isOk());
        List<Course> list = courseRepository.findAll();
        assertThat(list).hasSize(databaseSizebeforeupdate);
    }


}