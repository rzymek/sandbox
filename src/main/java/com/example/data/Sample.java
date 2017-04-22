package com.example.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Sample {
    @Id
    @GeneratedValue
    public Integer id;
    public String name;
}
