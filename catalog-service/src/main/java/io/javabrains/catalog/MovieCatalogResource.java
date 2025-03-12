package io.javabrains.catalog;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @GetMapping("/{userId}")
    public List<CatalogItem> getItems(@PathVariable("userId") String userId){
        return Collections.singletonList(
                new CatalogItem("Veer Zara", "Fantastic Love Story",4.9)
        );
    }
}
