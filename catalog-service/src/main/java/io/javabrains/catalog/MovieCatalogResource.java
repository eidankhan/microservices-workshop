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
        UserRating userRatings = restTemplate.getForObject("http://rating-info-service/ratings/users/" + userId, UserRating.class);
        // Get Movies using the RestTemplate
        return userRatings.getRatings().stream().map(rating -> {
            // Calling Eureka instances instead of hardcoded URLs
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

//            Movie movie = webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:8082/movies/"+rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();


            return new CatalogItem(movie.getName(), "Description", rating.getRatingValue());
        }).collect(Collectors.toList());
    }
}
