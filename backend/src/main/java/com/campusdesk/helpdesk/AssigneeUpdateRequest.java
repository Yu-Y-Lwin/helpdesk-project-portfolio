package com.campusdesk.helpdesk;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AssigneeUpdateRequest(
        @NotBlank @Pattern(regexp = ValidationRules.EMAIL_REGEX) String assignee) {
}
