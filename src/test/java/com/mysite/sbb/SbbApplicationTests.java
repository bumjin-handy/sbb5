package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository  answerRepository;

	@Test
	void testJpa() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);  // 첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);  // 두번째 질문 저장

		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());

		//findById 메서드 테스트
		Optional<Question> oq = this.questionRepository.findById(1);
		if(oq.isPresent()) {
			Question qById = oq.get();
			assertEquals("sbb가 무엇인가요?", qById.getSubject());
		}

		//findBySubject 메서드 테스트

        Question qBySubject = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        System.out.println(qBySubject.getSubject());
        assertNotNull(qBySubject);
        assertEquals("sbb가 무엇인가요?", qBySubject.getSubject());




		//findBySubjectAndContent 메서드 테스트
		Optional<Question> qBySubjectAndContent = this.questionRepository.findBySubjectAndContent(
				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertTrue(qBySubjectAndContent.isPresent());
		assertEquals(1, qBySubjectAndContent.get().getId());

		//findBySubjectLike 메서드 테스트
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question qBySubjectLike = qList.get(0);
		assertEquals("sbb가 무엇인가요?", qBySubjectLike.getSubject());

		//질문 데이터 수정하기
		Optional<Question> oqForUpdate = this.questionRepository.findById(1);
		assertTrue(oqForUpdate.isPresent());
		Question qForUpdate = oqForUpdate.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(qForUpdate);

		//질문 데이터 삭제하기
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oqForDelete = this.questionRepository.findById(1);
		assertTrue(oqForDelete.isPresent());
		Question qForDelete = oqForDelete.get();
		this.questionRepository.delete(qForDelete);
		assertEquals(1, this.questionRepository.count());

		//답변 데이터 저장하기
		Optional<Question> oqForAnswerSave = this.questionRepository.findById(2);
		assertTrue(oqForAnswerSave.isPresent());
		Question qForAnswerSave = oqForAnswerSave.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(qForAnswerSave);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);

		//답변 데이터 조회하기
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer aForAnswer = oa.get();
		assertEquals(2, aForAnswer.getQuestion().getId());


	}

	@Transactional
	@Test
	void testJpa2() {
		//질문 데이터를 통해 답변 데이터 찾기
		Optional<Question> oqFromQuestion = this.questionRepository.findById(2);
		assertTrue(oqFromQuestion.isPresent());
		Question qFromQuestion = oqFromQuestion.get();

		List<Answer> answerList = qFromQuestion.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
}
