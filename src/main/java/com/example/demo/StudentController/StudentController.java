package com.example.demo.StudentController;

import com.example.demo.StudentService.CourseService;
import com.example.demo.StudentService.StudentService;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class StudentController {

    @Autowired
    private StudentService service;
    @Autowired
    private CourseService courseService;

    @GetMapping("/students")
    public List<Student> list(){
       return service.findAll();
    }
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> get(@PathVariable Integer id){
        try{
            Student student = service.get(id);
            return new ResponseEntity<Student>(student,HttpStatus.OK);
        }catch (NoSuchElementException exception)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/students")
    public void add(@RequestBody Student student){
        service.save(student);
    }
    @DeleteMapping("/students/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    @PutMapping("/students/{id}")
    public ResponseEntity<?> update(@RequestBody Student student,@PathVariable Integer id){
        try{
            service.save(student);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException exception)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/students/courses")
    public List<Course> findAll()
    {
        return courseService.findAll();
    }

    @PostMapping("/students/courses")
    public void add(@RequestBody Course course)
    {
        courseService.save(course);
    }

    @GetMapping("/students/courses/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Integer id){
        try{
            Course course = courseService.get(id);
            return new ResponseEntity<Course>(course,HttpStatus.OK);
        }catch (NoSuchElementException exception)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/students/courses/{id}")
    public void deleteCourse(@PathVariable Integer id)
    {
        courseService.delete(id);
    }
    @PutMapping("/students/courses/{id}")
    public ResponseEntity<?> updateCourse(@RequestBody Course course,@PathVariable Integer id)
    {
        try{
            courseService.save(course);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException exception)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
