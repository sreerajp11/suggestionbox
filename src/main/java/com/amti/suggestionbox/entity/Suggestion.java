package com.amti.suggestionbox.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Schema(name = "Suggestion", description = "Entity representing a suggestion in the suggestion box system")
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title cannot be null")
    private String title;

    @NotNull(message = "Description cannot be null")
    private String description;
    private boolean isPublic = true;
    private LocalDateTime submittedAt = LocalDateTime.now();

    @Column(length = 800)
    @Schema(description = "Admin comment on the submission", maxLength = 1000)
    private String adminComment;

    @Schema(description = "Department associated", example = "HR")
    private String department;

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
