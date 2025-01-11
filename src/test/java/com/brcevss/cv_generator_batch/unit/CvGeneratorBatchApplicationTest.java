package com.brcevss.cv_generator_batch.unit;

import com.brcevss.cv_generator_batch.CvGeneratorBatchApplication;

import org.junit.jupiter.api.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class CvGeneratorBatchApplicationTest {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> SpringApplication.run(CvGeneratorBatchApplication.class));
	}

}
