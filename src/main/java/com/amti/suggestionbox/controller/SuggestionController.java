package com.amti.suggestionbox.controller;

import com.amti.suggestionbox.entity.Suggestion;
import com.amti.suggestionbox.repository.SuggestionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Tag(name = "Suggestion Controller", description = "Controller for managing suggestions")
public class SuggestionController {

    @Autowired
    private SuggestionRepository repository;

    @GetMapping("/")
    @Operation(summary = "Shows department selection page", description = "Displays the main page where users can select a department(HR/software) to submit suggestions.")
    public String showDepartmentSelectorPage() {
        return "index";
    }


    @GetMapping("/submit/hr")
    @Operation(summary = "Shows HR suggestion form", description = "Displays the HR suggestion form")
    public String showHRSuggestionForm(Model model) {
        Suggestion suggestion = new Suggestion();
        suggestion.setDepartment("HR");
        model.addAttribute("suggestion", suggestion);
        return "suggestions";
    }

    @GetMapping("/submit/software")
    @Operation(summary = "Shows Software suggestion form", description = "Displays the Software suggestion form")
    public String showSoftwareSuggestionForm(Model model) {
        Suggestion suggestion = new Suggestion();
        suggestion.setDepartment("SOFTWARE");
        model.addAttribute("suggestion", suggestion);
        return "suggestions";
    }


    @PostMapping("/submit")
    @Operation(summary = "Submits a suggestion", description = "Saves the suggestion to the database and redirects to the appropriate department")
    public String submitSuggestion(@ModelAttribute Suggestion suggestion, Model model) {
        repository.save(suggestion);
        return "redirect:/submit/" + suggestion.getDepartment().toLowerCase() + "?success";
    }


    @GetMapping("/public/hr")
    @Operation(summary = "View public HR suggestions", description = "Displays all public suggestions for the HR department")
    public String viewPublicHRSuggestions(Model model) {
        List<Suggestion> suggestions = repository.findByDepartmentAndIsPublicTrue("HR");
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("department", "HR");
        return "public-suggestions";
    }

    @GetMapping("/public/software")
    @Operation(summary = "View public Software suggestions", description = "Displays all public suggestions for the Software department")
    public String viewPublicSoftwareSuggestions(Model model) {
        List<Suggestion> suggestions = repository.findByDepartmentAndIsPublicTrue("SOFTWARE");
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("department", "SOFTWARE");
        return "public-suggestions";
    }
}
