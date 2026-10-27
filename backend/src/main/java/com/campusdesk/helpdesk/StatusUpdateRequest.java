package com.campusdesk.helpdesk;

import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(@NotNull Status status) {
}
