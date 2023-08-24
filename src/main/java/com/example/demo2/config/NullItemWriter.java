package com.example.demo2.config;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class NullItemWriter<T> implements ItemWriter<T> {
    
    @Override
    public void write(Chunk<? extends T> chunk) {
        // Do nothing, effectively discarding the items
    }
}