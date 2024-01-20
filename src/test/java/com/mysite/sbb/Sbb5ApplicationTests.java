package com.mysite.sbb;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Sbb5ApplicationTests {
	
	@Autowired
    private QuestionRepository questionRepository;

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

        Optional<Question> oq = this.questionRepository.findById(1);
        if(oq.isPresent()) {
            Question qById = oq.get();
            assertEquals("sbb가 무엇인가요?", qById.getSubject());
        }

        /*Question qBySubject = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        System.out.println(qBySubject.getSubject());
        assertNotNull(qBySubject);
        assertEquals("sbb가 무엇인가요?", qBySubject.getSubject());*/

        Optional<Question> qBySubject = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        //System.out.println(qList.size());
        assertTrue(qBySubject.isPresent());
        assertEquals("sbb가 무엇인가요?", qBySubject.get().getSubject());

        /*List<Question> qList = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        System.out.println(qList.size());
        assertTrue(!qList.isEmpty());*/



    }
}
