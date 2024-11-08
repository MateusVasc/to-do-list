package com.matt.to_do_list.dto.request;

import java.time.LocalDateTime;

public record UpdateItemRequest(
        String title,
        String description,
        LocalDateTime endsAt
) {
}
