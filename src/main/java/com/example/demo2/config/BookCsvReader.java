package com.example.demo2.config;


import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import com.example.demo2.model.Book;

public class BookCsvReader extends FlatFileItemReader<Book> {

    public BookCsvReader() {
        setResource(new FileSystemResource("/home/vikas/personal_work/demo2/src/main/resources/books.csv"));
        setLinesToSkip(1);

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("name", "author");

        BeanWrapperFieldSetMapper<Book> fieldMapper = new BeanWrapperFieldSetMapper<>();
        fieldMapper.setTargetType(Book.class);

        DefaultLineMapper<Book> lineMapper = new DefaultLineMapper<>();
        lineMapper.setFieldSetMapper(fieldMapper);
        lineMapper.setLineTokenizer(lineTokenizer);

        setLineMapper(lineMapper);
    }
}
