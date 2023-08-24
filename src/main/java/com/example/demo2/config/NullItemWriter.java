package com.example.demo2.config;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class NullItemWriter<T> implements ItemWriter<T> {
    
    @Override
    public void write(Chunk<? extends T> chunk) {
        System.out.println("=====================?bala");
        System.out.println(chunk);
        System.out.println("=====================?balabala2");

        
        // Do nothing, effectively discarding the items
    }
}