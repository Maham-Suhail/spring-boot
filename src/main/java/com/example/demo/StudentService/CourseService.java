package com.example.demo.StudentService;

import com.example.demo.StudentRepository.CourseRepository;
import com.example.demo.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Course save(Course course) {

        Course updateCourse = null;
        int id  = course.getId();
        Optional<Course> updateCourseOp = repository.findById(id);
        if(updateCourseOp.isPresent())
        {
            updateCourse = repository.findById(id).get();
            updateCourse.setId(course.getId());
            updateCourse.setName(course.getName());
            updateCourse.setCreditHour(course.getCreditHour());
           return repository.save(updateCourse);
        }
        else
        {
            return repository.save(course);
        }
    }

    public void delete(Integer id)
    {
        repository.deleteById(id);
    }

}
