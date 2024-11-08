package com.matt.to_do_list.dto.response;

import com.matt.to_do_list.model.Item;

import java.time.LocalDateTime;
import java.util.UUID;

public record ItemResponse(
        UUID id,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime endsAt,
        UUID ownerId
    ) {

    public ItemResponse(Item item) {
        this(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getCreatedAt(),
                item.getEndsAt(),
                item.getOwner().getId()
        );
    }
}
