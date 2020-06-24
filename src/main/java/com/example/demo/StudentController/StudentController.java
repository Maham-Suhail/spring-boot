package com.example.demo.StudentController;

import com.example.demo.StudentService.StudentService;
import com.example.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class StudentController {

    @Autowired
    private StudentService service;

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
}
