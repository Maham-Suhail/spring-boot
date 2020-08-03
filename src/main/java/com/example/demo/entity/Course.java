package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "course")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Course {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int course_id;
    @Column(name = "name")
    private String name;
    @Column(name = "credit_hour")
    private int creditHour;

    public Course(){}
    public Course(int id, String name, int creditHour) {
        this.course_id = id;
        this.name = name;
        this.creditHour = creditHour;
    }

    public int getId() {
        return course_id;
    }

    public void setId(int id) {
        this.course_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreditHour() {
        return creditHour;
    }

    public void setCreditHour(int creditHour) {
        this.creditHour = creditHour;
    }


    @Override
    public String toString() {
        return "Course{" +
                "course_id=" + course_id +
                ", name='" + name + '\'' +
                ", creditHour=" + creditHour +
                '}';
    }
}
