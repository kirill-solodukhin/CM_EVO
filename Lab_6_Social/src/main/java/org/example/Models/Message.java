package org.example.Models;

import java.time.LocalDateTime;
import java.util.List;

public record Message (
        Integer authorId,
        List<Integer> likes,
        Integer messageId,
        LocalDateTime sendDate,
        String text
) {}
