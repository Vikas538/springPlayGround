package com.example.demo2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo2.model.Book;
import com.example.demo2.repository.BooksRepository;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@RestController
public class HelloController {

	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

	@Autowired
	private ProducerService producerService;

	@Autowired
	private BooksRepository booksRepository;

		  @Autowired
			private JobLauncher jobLauncher;
	
    @Autowired
    private Job job;

	@GetMapping("/")
	public int index() {
		int result = producerService.factorial(5);
		producerService.sendMessage("testTopic", "hello-world");
		return result;
	}

	@PostMapping(value="/createBook", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String create(@RequestBody Book body) {
		logger.info("User: {}", body);
		System.out.println(body.toString());
		Book book = new Book(body.name,body.author);
		book.setAuthor(book.author);
		booksRepository.save(book);
		//  booksRepository.fidnBookByName("test");

		// System.out.println(book);
		return "created";
	}
	


    @PostMapping("/importCustomers")
    public void importCsvToDBJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

}
