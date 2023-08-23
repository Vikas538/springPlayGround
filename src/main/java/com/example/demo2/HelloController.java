package com.example.demo2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class HelloController {

	@Autowired
	private ProducerService producerService;

	@GetMapping("/")
	public int index() {
		int result = producerService.factorial(5);
		producerService.sendMessage("testTopic", "hello-world");
		return result;
	}

}
