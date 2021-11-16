package com.example.narrative.services;

import com.example.narrative.entities.Genre;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenre(UUID id) {
        return genreRepository.findById(id.toString()).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public Genre getGenre(String name) {
        return genreRepository.findByName(name).orElseThrow(() -> new NotFoundException(name + " does not exist"));
    }

    public Genre create(Genre genre) {
        genre.setId(UUID.randomUUID().toString());
        return genreRepository.save(genre);
    }

    public Genre update(Genre otherGenre, UUID id) {
        Genre existingGenre = getGenre(id);
        updateWith(otherGenre, existingGenre);
        return genreRepository.save(existingGenre);
    }

    public Genre update(Genre otherGenre, String name) {
        Genre existingGenre = getGenre(name);
        updateWith(otherGenre, existingGenre);
        return genreRepository.save(existingGenre);
    }

    public void updateWith(Genre otherGenre, Genre existingGenre) {
        existingGenre.setName(otherGenre.getName());
        existingGenre.setDescription(otherGenre.getDescription());
        existingGenre.setAddOnInstructions(otherGenre.getAddOnInstructions());
        existingGenre.setRequiredInstructions(otherGenre.getRequiredInstructions());
    }

    public Genre delete(UUID id){
        Genre genre = getGenre(id);
        genreRepository.delete(genre);
        return genre;
    }
}
