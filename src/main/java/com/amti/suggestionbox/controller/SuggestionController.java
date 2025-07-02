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
public class SuggestionController {

    @Autowired
    private SuggestionRepository repository;

    @GetMapping("/submit")
    public String showForm(Model model) {
        model.addAttribute("suggestion", new Suggestion());
        return "submit";
    }


    @PostMapping("/submit")
    public String submitSuggestion(@ModelAttribute Suggestion suggestion, @RequestParam(value = "makePrivate", required = false) String makePrivate
            , RedirectAttributes attributes) {
        if (makePrivate != null) {
            suggestion.setPublic(false);
        } else {
            suggestion.setPublic(true);
        }
        repository.save(suggestion);

        attributes.addFlashAttribute("success", "Suggestion submitted successfully!");
        return "redirect:/submit?success";
    }

    @GetMapping("/public")
    public String viewSuggestions(Model model) {
        List<Suggestion> publicSuggestion = repository.findByIsPublicTrue();
        model.addAttribute("suggestions", publicSuggestion);
        return "public";
    }


    @GetMapping("/admin/suggestions")
    public String viewAllSuggestion(Model model) {
        List<Suggestion> allSuggestions = repository.findAll();
        model.addAttribute("suggestions", allSuggestions);
        return "admin";
    }

    @PostMapping("/admin/comment/{id}")
    public String addHRComment(@PathVariable Long id, @RequestParam String hrComment, RedirectAttributes attributes) {
        Optional<Suggestion> optionalSuggestion = repository.findById(id);
        if (optionalSuggestion.isPresent()) {
            Suggestion suggestion = optionalSuggestion.get();
            if (!suggestion.isPublic()) {
                attributes.addFlashAttribute("error", "Cannot add comment to a private suggestion!");
                return "redirect:/admin/suggestions";
            }
            suggestion.setHrComment(hrComment);
            repository.save(suggestion);
            attributes.addFlashAttribute("success", "Comment added successfully!");
        } else {
            attributes.addFlashAttribute("error", "Suggestion not found!");
        }
        return "redirect:/admin/suggestions";
    }

}
