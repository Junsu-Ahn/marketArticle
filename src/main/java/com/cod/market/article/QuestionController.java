package com.cod.market.article;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String showCreateForm(Model model) {
        model.addAttribute("questionForm", new QuestionForm());
        return "article/create";
    }

    @PostMapping("/article/save")
    public String saveArticle(@Valid @ModelAttribute QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/create";
        }
        questionService.saveQuestion(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/article"; // 저장 후 목록 페이지로 리다이렉트
    }

    @GetMapping("/article/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Question question = questionService.findById(id);
        QuestionForm questionForm = new QuestionForm();
        questionForm.setId(question.getId());
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        model.addAttribute("questionForm", questionForm);
        return "article/create";
    }

    @PostMapping("/article/edit/{id}")
    public String updateArticle(@PathVariable("id") Integer id, @Valid @ModelAttribute QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/create";
        }
        questionService.updateQuestion(id, questionForm.getSubject(), questionForm.getContent());
        return "redirect:/article/detail/" + id;
    }

    @PostMapping("/article/delete")
    public String deleteArticle(@RequestParam("id") Integer id) {
        questionService.deleteQuestion(id);
        return "redirect:/article";
    }

}