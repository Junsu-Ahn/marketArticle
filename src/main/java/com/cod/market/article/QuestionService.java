package com.cod.market.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question findById(Integer id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            return optionalQuestion.get();
        } else {
            throw new QuestionNotFoundException("Question not found with id: " + id);
        }
    }

    public Question saveQuestion(String subject, String content) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now()); // 현재 시간 설정

        return questionRepository.save(question);
    }
}


