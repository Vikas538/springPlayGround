package com.example.demo2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo2.model.Book;
import com.example.demo2.repository.BooksRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

@RestController
public class HelloController {

	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

	@Autowired
	private ProducerService producerService;

	@Autowired
	private BooksRepository booksRepository;

	@GetMapping("/")
	public int index() {
		int result = producerService.factorial(5);
		producerService.sendMessage("testTopic", "hello-world");
		return result;
	}

	@PostMapping(value="/createBook", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String create(@RequestBody Book body) {
				logger.info("User: {}", body);
				// Book book = new Book();
		// book.setAuthor(book.author);
		// booksRepository.save(book);
		 booksRepository.fidnBookByName("test");

		// System.out.println(book);
		return "created";
	}

}
