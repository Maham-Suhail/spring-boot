package com.example.demo.StudentService;

import com.example.demo.StudentRepository.CourseRepository;
import com.example.demo.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseService {

    @Autowired
    CourseRepository repository;

    public List<Course> findAll()
    {
        return repository.findAll();
    }
    public Course get(Integer id)
    {
        return repository.findById(id).get();
    }
    public Course save(Course course)
    {
        return repository.save(course);
    }
    public void delete(Integer id)
    {
        repository.deleteById(id);
    }
}
