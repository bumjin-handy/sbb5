package com.mysite.sbb;

import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.QuestionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SbbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbbApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(QuestionService questionService, AnswerService answerService) {
		return (args) -> {
			//delete all
			//questionService.deleteAll(); delete 쿼리가 row만큼 수행
			/*answerService.deleteAllInBatch();
			questionService.deleteAllInBatch();
			// save a few questions
			for (int i = 1; i <= 300; i++) {
				String subject = String.format("테스트 데이터입니다:[%03d] - [%03d]", i, i);
				String content = "내용무";
				questionService.create(subject, content, null);
			}*/
		};
	}
}
