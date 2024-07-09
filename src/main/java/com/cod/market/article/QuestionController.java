package com.cod.market.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    @GetMapping("/article")
    public String list(Model model) {
        List<Question> questionList = this.questionRepository.findAll();
        model.addAttribute("questionList", questionList);
        return "/article/articleList";
    }

    @GetMapping(value = "/article/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = questionService.findById(id); // questionService에서 해당 id의 질문을 가져옵니다.
        model.addAttribute("question", question); // 모델에 question 객체를 추가합니다.
        return "/article/articleDetail";
    }

    @GetMapping("/article/create")
    public String showCreateForm() {
        return "/article/create";
    }

    @PostMapping("/article/save")
    public String saveArticle(@RequestParam("subject") String subject,
                              @RequestParam("content") String content) {
        questionService.saveQuestion(subject, content);
        return "redirect:/article"; // 저장 후 목록 페이지로 리다이렉트
    }
}