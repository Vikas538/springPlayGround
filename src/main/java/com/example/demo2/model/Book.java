package com.example.demo2.model;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BOOK")
public class Book {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;
    @Column(name = "name")
    public String name;
    @Column(name = "author")
    public String author;
    @Column(name = "createdAt")
    @CreatedDate
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Date createdAt;
    @Column(name = "updatedAt")
    @UpdateTimestamp
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Date updatedAt;

    public Book() {
    
    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
        // this.createdAt = new Date();
        // this.updatedAt = new Date();
    }
    
     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        // this.updatedAt = new Date();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        // this.updatedAt = new Date();
    }

}
