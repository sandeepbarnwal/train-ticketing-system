package com.cloudbees.tms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Activate the test profile
class TmsApplicationTests {

	@Test
	void contextLoads() {
	}

}
