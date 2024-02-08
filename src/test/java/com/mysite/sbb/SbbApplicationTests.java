package com.mysite.sbb;

import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;
	
	@DisplayName("게시판 데이타 5개 입력")
	@Transactional
	@Test
	/**
	 * Transactional annotation이 없어 롤백되지 않고 실행시마다 추가됨.
	 */
	void testJpa() {
		for (int i = 1; i <= 5; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d] - [%03d]", i, i);
			String content = "내용무";
			this.questionService.create(subject, content, null);
		}
	}

	@DisplayName("게시판 질문/답변 데이타 배치로 삭제")
	@Test
	void testBatchDeleteAllJpa() {
		//기존 데이터 삭제
		this.answerService.deleteAllInBatch();//답변이 있으면 질문삭제가 안되므로
		
		for (int i = 1; i <= 5; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d] - [%03d]", i, i);
			String content = "내용무";
			this.questionService.create(subject, content, null);
		}

		this.questionService.deleteAllInBatch();
	}

	@DisplayName("게시판 질문삭제")
	@Test
	void testDeleteAllJpa() {
		//기존 데이터 삭제
		this.answerService.deleteAllInBatch();//답변이 있으면 질문삭제가 안되므로
		for (int i = 1; i <= 5; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d] - [%03d]", i, i);
			String content = "내용무";
			this.questionService.create(subject, content, null);
		}
		this.questionService.deleteAll();
	}
}
