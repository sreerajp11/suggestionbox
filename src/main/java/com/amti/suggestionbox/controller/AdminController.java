package com.amti.suggestionbox.controller;

import com.amti.suggestionbox.entity.Suggestion;
import com.amti.suggestionbox.repository.SuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SuggestionRepository repository;


    @GetMapping("/hr")
    public String viewHRSuggestions(Model model) {
        List<Suggestion> suggestions = repository.findByDepartment("HR");
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("department", "HR");
        return "admin-suggestions";
    }

    @GetMapping("/software")
    public String viewSoftwareSuggestions(Model model) {
        List<Suggestion> suggestions = repository.findByDepartment("SOFTWARE");
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("department", "SOFTWARE");
        return "admin-suggestions";
    }

    @PostMapping("/reply/{dept}/{id}")
    public String handleReply(@PathVariable String dept,
                              @PathVariable Long id,
                              @RequestParam("adminComment") String comment,
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



