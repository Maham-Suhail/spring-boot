package com.example.demo.StudentService;

import com.example.demo.StudentRepository.StudentRepository;
import com.example.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student get(Integer id) {
       return repository.findById(id).get();
    }

    public Student save(Student student){
        return repository.save(student);
    }
    public void delete(Integer id){
        repository.deleteById(id);
    }


}
