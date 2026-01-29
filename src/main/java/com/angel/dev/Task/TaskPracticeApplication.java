package com.angel.dev.Task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TaskPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskPracticeApplication.class, args);
	}

}
