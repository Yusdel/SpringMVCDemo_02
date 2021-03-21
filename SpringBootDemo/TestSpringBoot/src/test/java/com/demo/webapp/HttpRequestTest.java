package com.demo.webapp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/*
 * @RunWith(SpringRunner.class) = The SpringRunner is the JUnit4 with which we will start our test
 * @SpringBootTest = this is a Test for Spring Boot Application, and execute on Random Port
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void testHomePage() throws Exception{
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class))
		.contains("Welcome into Hell");
	}
}
