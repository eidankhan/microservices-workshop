package io.javabrains.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingResource {
    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable Long movieId) {
        // Simulated rating retrieval from a database
        return new Rating(movieId, 4.29);
    }

    @GetMapping("/users/{userId}")
    public UserRating getUserRatings(@PathVariable Long userId) {
        // Simulated rating retrieval from a database
        List<Rating> ratings = Arrays.asList(
                new Rating(123L, 4.5),
                new Rating(456L, 3.8),
                new Rating(789L, 4.2)
        );
        return new UserRating(userId, ratings);
    }
}
