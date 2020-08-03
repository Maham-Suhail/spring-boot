package com.example.demo.StudentService;

import com.example.demo.StudentRepository.CourseRepository;
import com.example.demo.StudentRepository.StudentRepository;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student save(Student student){
        return repository.save(student);
    }
    public void delete(Integer id){
        repository.deleteById(id);
    }


    public boolean getById(Integer id) {
        return repository.findById(id).isPresent();
    }

    public ResponseEntity<String> addStudent(Student student) {

        Student newStudent = new Student();
        if(repository.findById(student.getId()).isPresent())
        {
            return ResponseEntity.badRequest().body("Id is already present");
        }
        else
        {
            newStudent.setFirstName(student.getFirstName());
            newStudent.setLastName(student.getLastName());
            newStudent.setEmail(student.getEmail());
            newStudent.setCourses(student.getCourses());
            Student savedStudent = repository.save(newStudent);
            if (repository.findById(savedStudent.getId()).isPresent())
                return ResponseEntity.ok("User Created Successfully");
            else return ResponseEntity.unprocessableEntity().body("Failed Creating User as Specified");
        }
    }

    public void saveUpdate(Student student) {
        Student updatedStudent = repository.findById(student.getId()).get();
        updatedStudent.setId(student.getId());
        updatedStudent.setFirstName(student.getFirstName());
        updatedStudent.setLastName(student.getLastName());
        updatedStudent.setEmail(student.getEmail());
        updatedStudent.setCourses(student.getCourses());
        repository.save(updatedStudent);
    }

    public Student get(Integer id) {
        return repository.findById(id).get();
    }
}
