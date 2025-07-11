package com.amti.suggestionbox.repository;

import com.amti.suggestionbox.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

    List<Suggestion> findByDepartmentAndIsPublicTrue(String department);

    List<Suggestion> findByDepartment(String department);
}