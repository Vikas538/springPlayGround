package com.example.demo2.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo2.model.Book;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class KafkaFactory {

    private DataSource dataSource;
    
      public  KafkaFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ProducerFactory<String, ?> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    @Bean
    public KafkaTemplate<String, ?> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    public FlatFileItemReader<Book> reader() {

        FlatFileItemReader<Book> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("/src/resources/books.csv"));
        itemReader.setName("csvreader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }
    
    private LineMapper<Book> lineMapper() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setNames("name", "author");
        DefaultLineMapper<Book> lineMapper = new DefaultLineMapper<>();
        BeanWrapperFieldSetMapper<Book> fieldMapper = new BeanWrapperFieldSetMapper<>();
        fieldMapper.setTargetType(Book.class);
        lineMapper.setFieldSetMapper(fieldMapper);
        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        return lineMapper;
    }

        @Bean
    public NullItemWriter<Book> nullableWriter() {
        return new NullItemWriter<>();
        
    }
    
@Bean
public Job importUserJob(JobRepository jobRepository,  Step step1) {
    return new JobBuilder("importUserJob", jobRepository)
      .incrementer(new RunIdIncrementer())
      .flow(step1)
      .end()
      .build();
}

@Bean
public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("step1", jobRepository)
            .<Book, Book>chunk(10, transactionManager)
            .reader(reader())
            .writer(nullableWriter())
            .taskExecutor(taskExecutor())
            .build();
}

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

      @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
 
}