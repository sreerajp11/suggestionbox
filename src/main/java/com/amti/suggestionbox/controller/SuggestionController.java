package com.amti.suggestionbox.controller;

import com.amti.suggestionbox.entity.Suggestion;
import com.amti.suggestionbox.repository.SuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SuggestionController {

    @Autowired
    private SuggestionRepository repository;

    @GetMapping("/")
    public String showDepartmentSelectorPage() {
        return "index";
    }


    @GetMapping("/submit/hr")
    public String showHRSuggestionForm(Model model) {
        Suggestion suggestion = new Suggestion();
        suggestion.setDepartment("HR");
        model.addAttribute("suggestion", suggestion);
        return "suggestions";
    }

    @GetMapping("/submit/software")
    public String showSoftwareSuggestionForm(Model model) {
        Suggestion suggestion = new Suggestion();
        suggestion.setDepartment("SOFTWARE");
        model.addAttribute("suggestion", suggestion);
        return "suggestions";
    }



    @PostMapping("/submit")
    public String submitSuggestion(@ModelAttribute Suggestion suggestion, Model model) {
        repository.save(suggestion);
        return "redirect:/submit/" + suggestion.getDepartment().toLowerCase() + "?success";
    }


    @GetMapping("/public/hr")
    public String viewPublicHRSuggestions(Model model) {
        List<Suggestion> suggestions = repository.findByDepartmentAndIsPublicTrue("HR");
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("department", "HR");
        return "public-suggestions";
    }

    @GetMapping("/public/software")
    public String viewPublicSoftwareSuggestions(Model model) {
        List<Suggestion> suggestions = repository.findByDepartmentAndIsPublicTrue("SOFTWARE");
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("department", "SOFTWARE");
        return "public-suggestions";
    }
}
