package org.example.Models;

import java.time.OffsetDateTime;

public record FriendsRequest (
        int fromUserId,
        OffsetDateTime sendDate,
        int status,
        int toUserId
) {}
