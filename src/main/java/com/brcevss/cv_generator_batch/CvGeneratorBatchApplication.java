package com.brcevss.cv_generator_batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CvGeneratorBatchApplication {

	private static final Logger log = LoggerFactory.getLogger(CvGeneratorBatchApplication.class);

	public static void main(String[] args) {
		try {
			log.info("Démarrage de l'application CvGeneratorBatchApplication...");
			SpringApplication.run(CvGeneratorBatchApplication.class, args);
			log.info("Application CvGeneratorBatchApplication démarrée avec succès.");
		} catch (Exception e) {
			log.error("Erreur lors du démarrage de l'application CvGeneratorBatchApplication : ", e);
		}
	}
}