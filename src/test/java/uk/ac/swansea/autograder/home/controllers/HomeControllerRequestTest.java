package uk.ac.swansea.autograder.home.controllers;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HomeControllerRequestTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void indexShouldReturnDefaultMessage() {
		assertThat(this.restTemplate.getForObject("/home",
				String.class)).contains("Welcome to Autograder Web Service");
	}
}
