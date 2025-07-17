package com.amti.suggestionbox.controller;

import com.amti.suggestionbox.entity.Suggestion;
import com.amti.suggestionbox.repository.SuggestionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Tag(name="Control for HR/software admins", description = "Operations related to HR and Software department suggestions")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SuggestionRepository repository;


    @GetMapping("/hr")
    @Operation(summary = "View HR suggestions", description = "Fetches all suggestions related to HR department")
    public String viewHRSuggestions(Model model) {
        List<Suggestion> suggestions = repository.findByDepartment("HR");
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("department", "HR");
        return "admin-suggestions";
    }

    @GetMapping("/software")
    @Operation(summary = "View Software suggestions", description = "Fetches all suggestions related to Software department")
    public String viewSoftwareSuggestions(Model model) {
        List<Suggestion> suggestions = repository.findByDepartment("SOFTWARE");
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("department", "SOFTWARE");
        return "admin-suggestions";
    }

    @PostMapping("/reply/{dept}/{id}")
    @Operation(summary = "Handle reply to a suggestion", description = "Allows admin to add a reply to a suggestion")
    public String handleReply(@Parameter(description = "Department selected that is to be retrieved") @PathVariable String dept,
                              @Parameter(description = "ID of the suggestion that is to be retrieved") @PathVariable Long id,
                              @Parameter(description = "The comment which admin gives for the given suggestion") @RequestParam("adminComment") String comment,
                              RedirectAttributes redirectAttributes) {

        Suggestion suggestion = repository.findById(id).orElse(null);
        if (suggestion != null) {
            suggestion.setAdminComment(comment);
            repository.save(suggestion);
            redirectAttributes.addFlashAttribute("success", "Reply added successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Suggestion not found.");
        }

        return "redirect:/admin/" + dept.toLowerCase();
    }
}



