package org.example.Models;

import java.time.OffsetDateTime;

public record User (
        OffsetDateTime dateOfBirth,
        Integer gender,
        OffsetDateTime lastVisit,
        String name,
        Boolean online,
        Integer userId
) {}
