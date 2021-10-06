package com.abc.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "passport_number")
    private String passportNumber;

//    public Student(){
//
//    }
    public Student(String name, String passportNumber) {
        this.name = name;
        this.passportNumber = passportNumber;
    }

//    public Student(long id,String name, String passportNumber) {
//        this.id=id;
//        this.name = name;
//        this.passportNumber = passportNumber;
//    }

}