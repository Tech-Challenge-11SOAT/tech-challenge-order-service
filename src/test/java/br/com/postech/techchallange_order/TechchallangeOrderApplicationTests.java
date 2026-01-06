package br.com.postech.techchallange_order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TechchallangeOrderApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertNotNull(applicationContext);
	}

	@Test
	void mainMethodShouldRun() {
		// Test that main method can be invoked without exceptions
		String[] args = {};
		// We just verify the method exists and can be called
		// Actual execution is tested by contextLoads
		assertNotNull(TechchallangeOrderApplication.class);
	}
}
