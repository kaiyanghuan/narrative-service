package com.example.narrative.controllers;

import com.example.narrative.controllers.requests.GenreRequest;
import com.example.narrative.controllers.responses.BriefGenreResponse;
import com.example.narrative.controllers.responses.GenreResponse;
import com.example.narrative.services.GenreService;
import com.example.narrative.utils.RequestHelper;
import com.example.narrative.utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private ResponseHelper responseHelper;

    @Autowired
    private RequestHelper requestHelper;

    @GetMapping
    public ResponseEntity<List<BriefGenreResponse>> getAllGenres() {
        return ok(genreService.getGenres().stream().map(genre -> responseHelper.from(genre).toBriefGenreResponse()).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponse> getGenre(@PathVariable UUID id) {
        return ok(responseHelper.from(genreService.getGenre(id)).toGenreResponse());
    }

    @GetMapping("/{name}/name")
    public ResponseEntity<GenreResponse> getGenre(@PathVariable String name) {
        return ok(responseHelper.from(genreService.getGenre(name)).toGenreResponse());
    }

    @PostMapping()
    public ResponseEntity<BriefGenreResponse> createGenre(@RequestBody GenreRequest genreRequest) {
        return ok(responseHelper.from(genreService.create(requestHelper.from(genreRequest).toGenre())).toBriefGenreResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BriefGenreResponse> updateGenre(@RequestBody GenreRequest genreRequest, @PathVariable UUID id) {
        return ok(responseHelper.from(genreService.update(requestHelper.from(genreRequest).toGenre(), id)).toBriefGenreResponse());
    }

    @PutMapping("/{name}/name")
    public ResponseEntity<BriefGenreResponse> updateGenre(@RequestBody GenreRequest genreRequest, @PathVariable String name) {
        return ok(responseHelper.from(genreService.update(requestHelper.from(genreRequest).toGenre(), name)).toBriefGenreResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenreResponse> deleteGenre(@PathVariable UUID id) {
        return ok(responseHelper.from(genreService.delete(id)).toGenreResponse());
    }
}

