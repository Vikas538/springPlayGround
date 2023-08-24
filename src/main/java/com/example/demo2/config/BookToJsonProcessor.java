package com.example.demo2.config;

import com.example.demo2.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.batch.item.ItemProcessor;

public class BookToJsonProcessor implements ItemProcessor<Book, String> {

    @Override
    public String process(Book book) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(book));

        return objectMapper.writeValueAsString(book);
    }
}
