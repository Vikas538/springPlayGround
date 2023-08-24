package com.example.demo2.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo2.model.Book;

import jakarta.transaction.Transactional;

@Repository
public interface BooksRepository extends JpaRepository<Book, UUID> {
    
// @Transactional   
// @Modifying   
// @Query(value="UPDATE Book SET name = :name where id = '1c1e753e-557b-4453-a91d-f86b0a247036'",nativeQuery = true)
//     int fidnBookByName(String name);
}
