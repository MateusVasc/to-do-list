package com.matt.to_do_list.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateItemRequest(
        String title,
        String description,
        LocalDateTime endsAt,
        UUID ownerId) {
}
