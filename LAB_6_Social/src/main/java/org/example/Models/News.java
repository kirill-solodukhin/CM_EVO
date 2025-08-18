package org.example.Models;

import java.util.List;

public record News (
        int authorId,
        String authorName,
        List<Integer> likes,
        String text
) {}
