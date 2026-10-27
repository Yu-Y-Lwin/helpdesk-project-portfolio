package com.campusdesk.helpdesk;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateTicketRequest(
        @NotBlank @Size(min = 3, max = 100) String title,
        @NotBlank @Size(min = 10, max = 1000) String description,
        Priority priority,
        @NotBlank @Pattern(regexp = ValidationRules.EMAIL_REGEX) String requesterEmail) {
}
