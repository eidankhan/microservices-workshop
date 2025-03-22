package io.javabrains.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @GetMapping("/{userId}")
    public List<CatalogItem> getItems(@PathVariable("userId") String userId){
        // Get Ratings (hardcoding ratings data for now)
        List<Rating> ratings = Arrays.asList(
                new Rating(123L, 4.5),
                new Rating(456L, 3.8),
                new Rating(789L, 4.2)
        );

        // Get Movies using the RestTemplate
        return ratings.stream().map(rating -> {
            // Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);

            Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/"+rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();


            return new CatalogItem(movie.getName(), "Description", rating.getRatingValue());
        }).collect(Collectors.toList());
    }
}
