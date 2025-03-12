package io.javabrains.rating;

public class Rating {
    private Long movieId;
    private Double ratingValue;

    public Rating(Long movieId, Double ratingValue) {
        this.movieId = movieId;
        this.ratingValue = ratingValue;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Double ratingValue) {
        this.ratingValue = ratingValue;
    }
}
